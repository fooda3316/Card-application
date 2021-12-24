package com.example.mycards.anim;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;


import com.fooda.mycards.R;

import java.io.IOException;

public class IntroVideoView extends SurfaceView implements SurfaceHolder.Callback {
    private MediaPlayer c;

    public IntroVideoView(Context paramContext) {
        super(paramContext);
        a();
    }

    public IntroVideoView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        a();
    }

    public IntroVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        a();
    }

    @TargetApi(21)
    public IntroVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
        a();
    }

    private void a() {
        this.c = new MediaPlayer();
        getHolder().addCallback(this);
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.intro);
        try {
            this.c.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
            this.c.prepare();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i = getHeight();
        int j = getWidth();
        layoutParams.height = i;
        layoutParams.width = j;
        setLayoutParams(layoutParams);
        this.c.setDisplay(getHolder());
        this.c.setLooping(false);
        this.c.start();
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        this.c.stop();
    }
}

