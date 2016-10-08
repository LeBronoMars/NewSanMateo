package sanmateo.com.profileapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.com.profileapp.R;
import sanmateo.com.profileapp.adapters.GalleryAdapter;
import sanmateo.com.profileapp.base.BaseActivity;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.fragments.GalleryDetailFragment;
import sanmateo.com.profileapp.helpers.ApiRequestHelper;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.Gallery;
import sanmateo.com.profileapp.singletons.CurrentUserSingleton;
import sanmateo.com.profileapp.singletons.GalleriesSingleton;

/**
 * Created by rsbulanon on 6/27/16.
 */
public class GalleryActivity extends BaseActivity implements OnApiRequestListener {
    @BindView(R.id.rv_photos) RecyclerView rv_photos;
    private CurrentUserSingleton currentUserSingleton;
    private GalleriesSingleton galleriesSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setToolbarTitle("Gallery");
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.getInstance();
        galleriesSingleton = GalleriesSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        initRecycler();
    }


    private void initRecycler() {
        final GalleryAdapter adapter = new GalleryAdapter(this, galleriesSingleton.getGalleries());
        adapter.setOnGalleryClickListener(gallery -> {
            final GalleryDetailFragment fragment = GalleryDetailFragment.newInstance(gallery);
            fragment.show(getFragmentManager(), "Photo Details");
        });
        rv_photos.setAdapter(adapter);
        rv_photos.setLayoutManager(new LinearLayoutManager(this));

        if (galleriesSingleton.getGalleries().size() == 0) {
            apiRequestHelper.getGalleries(token);
        }
    }

    @Override
    public void onApiRequestBegin(ApiAction action) {
        if (action.equals(ApiAction.GET_PHOTOS)) {
            showCustomProgress("Fetching gallery photos, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(ApiAction action, Object result) {
        if (action.equals(ApiAction.GET_PHOTOS)) {
            final ArrayList<Gallery> galleries = (ArrayList<Gallery>) result;
            galleriesSingleton.getGalleries().clear();
            galleriesSingleton.getGalleries().addAll(galleries);
        }
        rv_photos.getAdapter().notifyDataSetChanged();
        dismissCustomProgress();
    }

    @Override
    public void onApiRequestFailed(ApiAction action, Throwable t) {
        dismissCustomProgress();
    }
}
