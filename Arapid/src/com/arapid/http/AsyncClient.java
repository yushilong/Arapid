package com.arapid.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.arapid.MainApplication;
import com.arapid.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;

/**
 * Created by yushilong on 2015/1/22.
 */
public class AsyncClient {
    public static final String TAG = "HTTP";
    private AsyncHttpClient _instance;
    private boolean cacheEnable = false;
    private String url;
    private View progressView;
    private IAsyncClientImpl iAsyncClient;
    private Context context = MainApplication.getInstance();
    private JSONObject paramObject;
    public static final String CONTENT_TYPE = "application/json";
    public static final String UTF8_BOM = "\uFEFF";
    private ResponseHandlerInterface responseHandlerInterface;
    private Method method = Method.POST;

    public AsyncClient(String url, View progressView, IAsyncClientImpl iAsyncClient) {
        this.url = url;
        this.progressView = progressView;
        this.iAsyncClient = iAsyncClient;
        this._instance = new AsyncHttpClient();
        init();
    }

    public enum Method{
        GET,POST,PUT
    }

    private void init() {
        _instance.addHeader("Content-TYpe",CONTENT_TYPE);
        responseHandlerInterface = new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                iAsyncClient.start();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                parseSuccess(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                parseSuccess(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                parseSuccess(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                parseFailure(statusCode, context.getString(R.string.error_datafetch));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                parseFailure(statusCode, context.getString(R.string.error_datafetch));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                parseFailure(statusCode, context.getString(R.string.error_datafetch));
            }


            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                iAsyncClient.progress(bytesWritten, totalSize);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                iAsyncClient.finish();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                iAsyncClient.cancel();
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                iAsyncClient.retry(retryNo);
            }
        };
    }

    /**
     * 发起请求
     */
    public void excute() {
        Log.i(TAG, "url--->" + url);
        checkCache();
        if (!isNetworkConnected()) {
            parseFailure(HttpStatus.SC_BAD_GATEWAY, context.getString(R.string.error_network));
            return;
        }
        if (method == Method.GET){
            _instance.get(context,url,responseHandlerInterface);
        }else if (method==Method.POST){
            _instance.post(context, url, getHttpEntity(),CONTENT_TYPE,responseHandlerInterface);
        }else if (method == Method.PUT){
            _instance.put(context,url,getHttpEntity(),CONTENT_TYPE,responseHandlerInterface);
        }
    }

    /**
     * 检查缓存
     */
    private void checkCache() {
        if (cacheEnable) {
            Object result = null;
            String jsonString = CacheManager.getInstance().readObject(url).toString();
            if (jsonString.startsWith(UTF8_BOM)) {
                jsonString = jsonString.substring(1);
            }
            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                try {
                    result = new JSONTokener(jsonString).nextValue();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (result == null)
                result = jsonString;
            parseSuccess(result);
        }
    }

    /**
     * success解析
     *
     * @param object
     */
    private void parseSuccess(Object object) {
        boolean isSuccess = false;
        int errorCode = Integer.MAX_VALUE;
        String errorStr = null;
        if (object instanceof JSONObject) {

        } else if (object instanceof JSONArray) {

        } else if (object instanceof String) {

        }
        //解析约定
        //
        if (isSuccess) {
            if (object instanceof JSONObject) {
                iAsyncClient.success((JSONObject) object);
            } else if (object instanceof JSONArray) {
                iAsyncClient.success((JSONArray) object);
            } else if (object instanceof String) {
                iAsyncClient.success((String) object);
            } else {
                throw new RuntimeException("data parse error");
            }
        } else {
            parseFailure(errorCode, errorStr);
        }
    }

    /**
     * failure解析
     *
     * @param statusCode
     * @param failureStr
     */
    private void parseFailure(int statusCode, String failureStr) {
        iAsyncClient.failure(statusCode, failureStr);
        Toast.makeText(context, failureStr, Toast.LENGTH_SHORT).show();
    }


    private HttpEntity getHttpEntity() {
        Log.i(TAG, "param--->" + paramObject.toString());
        if (paramObject == null)
            return null;
        try {
            return new StringEntity(paramObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public View getProgressView() {
        return progressView;
    }

    public void setProgressView(View progressView) {
        this.progressView = progressView;
    }

    public JSONObject getParamObject() {
        return paramObject;
    }

    public void setParamObject(JSONObject paramObject) {
        this.paramObject = paramObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
