package com.deepeye.musicpro.repository;

import com.deepeye.musicpro.db.AppDatabase;
import com.deepeye.musicpro.db.CachedTrack;
import com.deepeye.musicpro.db.SearchHistoryEntry;
import com.deepeye.musicpro.extractor.SearchService;
import com.deepeye.musicpro.extractor.StreamExtractor;
import com.deepeye.musicpro.model.SearchResult;
import com.deepeye.musicpro.model.StreamExtractionResult;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011J*\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00120\n2\u0006\u0010\u0016\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0018"}, d2 = {"Lcom/deepeye/musicpro/repository/SearchRepository;", "", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "searchService", "Lcom/deepeye/musicpro/extractor/SearchService;", "streamExtractor", "Lcom/deepeye/musicpro/extractor/StreamExtractor;", "(Lcom/deepeye/musicpro/db/AppDatabase;Lcom/deepeye/musicpro/extractor/SearchService;Lcom/deepeye/musicpro/extractor/StreamExtractor;)V", "extract", "Lkotlin/Result;", "Lcom/deepeye/musicpro/model/StreamExtractionResult;", "input", "", "extract-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recentSearches", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/deepeye/musicpro/db/SearchHistoryEntry;", "search", "Lcom/deepeye/musicpro/model/SearchResult;", "query", "search-gIAlu-s", "app_debug"})
public final class SearchRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.db.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.SearchService searchService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.StreamExtractor streamExtractor = null;
    
    public SearchRepository(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.db.AppDatabase database, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.SearchService searchService, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.StreamExtractor streamExtractor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.deepeye.musicpro.db.SearchHistoryEntry>> recentSearches() {
        return null;
    }
}