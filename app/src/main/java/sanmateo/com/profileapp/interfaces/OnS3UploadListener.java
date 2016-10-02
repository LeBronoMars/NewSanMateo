package sanmateo.com.profileapp.interfaces;

/**
 * Created by rsbulanon on 10/2/16.
 */
public interface OnS3UploadListener {

    void onUploadFinished(final String bucketName, final String imageUrl);
}
