package com.safewalk.stepupwalker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private static final String URL = "https://step-up-walker-simulator.baekihwan1234.chatgpt.site";
    private WebView webView;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(35, 56, 77));
        getWindow().setNavigationBarColor(Color.rgb(244, 246, 247));
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(244, 246, 247));
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) { return false; }
            @Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) showOffline();
            }
        });
        setContentView(webView);
        if (state == null) webView.loadUrl(URL); else webView.restoreState(state);
    }

    private void showOffline() {
        String html = "<html><meta name='viewport' content='width=device-width,initial-scale=1'><body style='font-family:sans-serif;background:#f4f6f7;color:#172a3a;display:flex;align-items:center;justify-content:center;text-align:center;height:100vh;margin:0'><div><h2>인터넷 연결을 확인해 주세요</h2><p>시뮬레이터를 불러오지 못했습니다.</p><button onclick=\"location.href='" + URL + "'\" style='padding:12px 20px;border:0;border-radius:8px;background:#23384d;color:white'>다시 시도</button></div></body></html>";
        webView.loadDataWithBaseURL(URL, html, "text/html", "UTF-8", null);
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }
    @Override protected void onSaveInstanceState(Bundle out) { webView.saveState(out); super.onSaveInstanceState(out); }
    @Override protected void onDestroy() { if (webView != null) webView.destroy(); super.onDestroy(); }
}
