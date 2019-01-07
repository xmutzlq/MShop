package google.architecture.personal;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class QrCodeManage {

    private static QrCodeManage instance;
    private SoftReference<Bitmap> qrCodeBitmap;

    public static QrCodeManage getInstance() {
        if(instance == null) {
            instance = new QrCodeManage();
        }
        return instance;
    }

    public void setQrCodeBitmap(Bitmap bitmap) {
        qrCodeBitmap = new SoftReference<>(bitmap);
    }

    public Bitmap getQrCodeBitmap() {
        return qrCodeBitmap != null && qrCodeBitmap.get() != null ? qrCodeBitmap.get() : null;
    }

    public void release() {
        if(qrCodeBitmap != null && qrCodeBitmap.get() != null) {
            qrCodeBitmap.get().recycle();
        }
        instance = null;
    }
}
