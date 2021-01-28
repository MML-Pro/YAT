package com.test.yatfirstexercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyOperators {

        private static RequestQueue mRequestQueue;
        private static ImageLoader mImageLoader;

        public static RequestQueue getInstance(Context context) {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            }

            return mRequestQueue;

        }

        public static ImageLoader getImageLoader(Context context){
            if(mImageLoader == null){
                mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                    private final LruCache<String,Bitmap> mCache = new LruCache<>((int) Runtime.getRuntime().maxMemory());

                    @Override
                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url,bitmap);
                    }
                });
            }
            return mImageLoader;

        }
}
