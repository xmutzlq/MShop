package google.architecture.coremodel.datamodel.http.upload;

import android.content.Context;

import net.gotev.uploadservice.HttpUploadRequest;
import net.gotev.uploadservice.UploadTask;

import java.net.MalformedURLException;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class Base64UploadRequest extends HttpUploadRequest {
    /**
     * Creates a new http upload request.
     *
     * @param context   application context
     * @param uploadId  unique ID to assign to this upload request. If is null or empty, a random
     *                  UUID will be automatically generated. It's used in the broadcast receiver
     *                  when receiving updates.
     * @param serverUrl URL of the server side script that handles the request
     * @throws IllegalArgumentException if one or more arguments are not valid
     * @throws MalformedURLException    if the server URL is not valid
     */
    public Base64UploadRequest(Context context, String uploadId, String serverUrl) throws MalformedURLException, IllegalArgumentException {
        super(context, uploadId, serverUrl);
    }

    public Base64UploadRequest(final Context context, final String serverUrl)
            throws MalformedURLException, IllegalArgumentException {
        this(context, null, serverUrl);
    }

    @Override
    protected Class<? extends UploadTask> getTaskClass() {
        return Base64UploadTask.class;
    }
}
