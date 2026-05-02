package com.deepeye.musicpro.ui.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.R
import com.deepeye.musicpro.adblock.AdBlockingWebView

class WebFallbackFragment : Fragment() {
    private var webView: AdBlockingWebView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.screen_web_fallback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = arguments?.getString(ARG_URL).orEmpty()
        view.findViewById<TextView>(R.id.webFallbackTitle).text = getString(R.string.error_extraction_failed)
        webView = view.findViewById<AdBlockingWebView>(R.id.adBlockingWebView).also {
            if (url.isNotBlank()) it.loadFallback(url, DeepEyeApp.from(requireContext()).adBlockEngine)
        }
    }

    override fun onDestroyView() {
        webView?.release()
        webView = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_URL = "arg_url"
        fun newInstance(url: String): WebFallbackFragment = WebFallbackFragment().apply {
            arguments = Bundle().apply { putString(ARG_URL, url) }
        }
    }
}
