package com.deepeye.musicpro.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.*
import org.json.JSONObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object YoutubeAuthManager {
    private const val CLIENT_ID = "1098654321-fake-client-id.apps.googleusercontent.com" // User should replace this
    private val SCOPES = listOf(
        "https://www.googleapis.com/auth/youtube.readonly",
        "https://www.googleapis.com/auth/youtube.force-ssl",
        "email",
        "profile"
    )
    
    private var context: Context? = null
    
    fun init(ctx: Context) {
        context = ctx.applicationContext
    }

    private val prefs by lazy {
        val ctx = context ?: throw IllegalStateException("AuthManager not initialized")
        try {
            val masterKey = MasterKey.Builder(ctx)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            EncryptedSharedPreferences.create(
                ctx,
                "youtube_auth_secure",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            com.deepeye.musicpro.util.Logger.e("YoutubeAuth", "EncryptedSharedPreferences failed, falling back to standard prefs", e)
            ctx.getSharedPreferences("youtube_auth_insecure", Context.MODE_PRIVATE)
        }
    }
    
    fun signIn(activity: Activity, launcher: ActivityResultLauncher<Intent>) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestServerAuthCode(CLIENT_ID, true) // For offline access / refresh token
            .apply { SCOPES.forEach { requestScopes(Scope(it)) } }
            .build()
            
        val client = GoogleSignIn.getClient(activity, gso)
        launcher.launch(client.signInIntent)
    }
    
    fun handleSignInResult(data: Intent?, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val authCode = account?.serverAuthCode
            if (authCode != null) {
                exchangeCodeForToken(authCode, account, onSuccess, onError)
            } else {
                onError(Exception("Auth code is null"))
            }
        } catch (e: ApiException) {
            onError(e)
        }
    }
    
    private fun exchangeCodeForToken(code: String, account: GoogleSignInAccount, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // In a real app, this should be done via a secure backend or using direct Google OAuth2 token endpoint
                // For this implementation, we assume we get the tokens from the exchange
                val client = OkHttpClient()
                val body = FormBody.Builder()
                    .add("code", code)
                    .add("client_id", CLIENT_ID)
                    .add("client_secret", "") // Secret is usually required for server-side, but Android uses different flow
                    .add("grant_type", "authorization_code")
                    .add("redirect_uri", "")
                    .build()
                
                val request = Request.Builder()
                    .url("https://oauth2.googleapis.com/token")
                    .post(body)
                    .build()
                
                val response = client.newCall(request).execute()
                val json = JSONObject(response.body?.string() ?: "{}")
                
                if (json.has("access_token")) {
                    val accessToken = json.getString("access_token")
                    val refreshToken = json.optString("refresh_token")
                    val expiresIn = json.getLong("expires_in")
                    
                    prefs.edit().apply {
                        putString("access_token", accessToken)
                        if (refreshToken.isNotEmpty()) putString("refresh_token", refreshToken)
                        putLong("expires_at", System.currentTimeMillis() + (expiresIn * 1000))
                        putString("user_email", account.email)
                        putString("user_name", account.displayName)
                        putString("user_photo", account.photoUrl?.toString())
                        apply()
                    }
                    withContext(Dispatchers.Main) { onSuccess(accessToken) }
                } else {
                    withContext(Dispatchers.Main) { onError(Exception("Failed to exchange code: ${json.optString("error_description")}")) }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError(e) }
            }
        }
    }
    
    fun getAccessToken(): String? {
        val expiresAt = prefs.getLong("expires_at", 0)
        if (System.currentTimeMillis() > expiresAt - 300000) { // 5 mins buffer
            return refreshAccessToken()
        }
        return prefs.getString("access_token", null)
    }
    
    private fun refreshAccessToken(): String? {
        val refreshToken = prefs.getString("refresh_token", null) ?: return null
        // Sync refresh for simplicity in getAccessToken call (not ideal but works for foreground fetch)
        return runBlocking(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val body = FormBody.Builder()
                    .add("client_id", CLIENT_ID)
                    .add("refresh_token", refreshToken)
                    .add("grant_type", "refresh_token")
                    .build()
                
                val request = Request.Builder()
                    .url("https://oauth2.googleapis.com/token")
                    .post(body)
                    .build()
                
                val response = client.newCall(request).execute()
                val json = JSONObject(response.body?.string() ?: "{}")
                
                if (json.has("access_token")) {
                    val newToken = json.getString("access_token")
                    val expiresIn = json.getLong("expires_in")
                    prefs.edit().apply {
                        putString("access_token", newToken)
                        putLong("expires_at", System.currentTimeMillis() + (expiresIn * 1000))
                        apply()
                    }
                    newToken
                } else null
            } catch (e: Exception) { null }
        }
    }
    
    fun isSignedIn(): Boolean = prefs.getString("access_token", null) != null
    fun getUserEmail(): String? = prefs.getString("user_email", null)
    fun getUserName(): String? = prefs.getString("user_name", null)
    fun getUserPhoto(): String? = prefs.getString("user_photo", null)
    
    fun signOut() {
        prefs.edit().clear().apply()
        context?.let { GoogleSignIn.getClient(it, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut() }
    }
}
