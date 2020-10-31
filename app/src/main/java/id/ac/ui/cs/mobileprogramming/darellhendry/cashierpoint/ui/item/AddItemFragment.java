package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Item;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.utils.AppExecutor;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.utils.BitmapUtils;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {

    static final int REQUEST_IMAGE_GET = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.fileprovider";
    private String mTempPhotoPath;
    private AppExecutor mAppExcutor;
    private Bitmap mResultsBitmap;
    private ImageView mImageView;
    private EditText mNameInput;
    private EditText mPriceInput;
    private ItemViewModel itemViewModel;
    private int takeOrChoose = 1;
    private Uri fullPhotoUri;
    private Cursor returnCursor;
    private int nameIndex;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name = mNameInput.getText().toString();
        String price = mPriceInput.getText().toString();
        if (item.getItemId() == R.id.action_save) {
            if (name.equals("")
                    || price.equals("")
                    || mImageView.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_menu_gallery).getConstantState()) {
                Toast.makeText(getContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
                return false;
            }

            processUpload(name, price);
            MainActivity.navController.navigate(R.id.nav_item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("imageUri", fullPhotoUri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mAppExcutor = new AppExecutor();
        mImageView = root.findViewById(R.id.image_view_preview);
        mNameInput = root.findViewById(R.id.name_input);
        mPriceInput = root.findViewById(R.id.price_input);

        if (savedInstanceState != null) {
            fullPhotoUri = savedInstanceState.getParcelable("imageUri");
            if (fullPhotoUri != null)
                mImageView.setImageURI(fullPhotoUri);
        }

        
        root.findViewById(R.id.button_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    launchCamera(getActivity());
                }
            }
        });

        root.findViewById(R.id.button_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }

            }
        });
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera(getActivity());
            } else {
                Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processAndSetImage();
            takeOrChoose = 1;
        } else {
            if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
                fullPhotoUri = data.getData();
                mImageView.setImageURI(data.getData());
                takeOrChoose = 0;
            }

        }
    }

    private void launchCamera(FragmentActivity fragmentActivity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(fragmentActivity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(fragmentActivity.getApplicationContext());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                mTempPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(fragmentActivity.getApplicationContext(),
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);
                fullPhotoUri = photoURI;
                Log.d("test", String.valueOf(photoURI));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void processAndSetImage() {
        mImageView.setVisibility(View.VISIBLE);

        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(getContext(), mTempPhotoPath);

        // Set the new bitmap to the ImageView
        mImageView.setImageBitmap(mResultsBitmap);
    }

    private void processUpload(String name, String price) {
        Log.d("test", String.valueOf("processupload"));
        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();

        returnCursor = getActivity().getContentResolver().query(fullPhotoUri, null, null, null, null);
        nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameIndex);
        Item newItem = new Item(name, Integer.parseInt(price), fileName);
        itemViewModel.insert(newItem, bitmap);
        if (takeOrChoose == 1) {
            mAppExcutor.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // Delete the temporary image file
                    BitmapUtils.deleteImageFile(AddItemFragment.this.getContext(), mTempPhotoPath);

                    // Save the image
                    BitmapUtils.saveImage(AddItemFragment.this.getContext(), mResultsBitmap);
                }
            });
        }
    }
}