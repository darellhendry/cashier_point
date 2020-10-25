package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.item;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
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
        switch (item.getItemId()) {
            case R.id.action_save:
                if (name.equals("")
                        || price.equals("")
                        || mImageView.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_menu_gallery).getConstantState()) {
                    Toast.makeText(getContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Item newItem = new Item(name, Integer.parseInt(price), "");
                itemViewModel.insert(newItem);
                mAppExcutor.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Delete the temporary image file
                        BitmapUtils.deleteImageFile(AddItemFragment.this.getContext(), mTempPhotoPath);

                        // Save the image
                        BitmapUtils.saveImage(AddItemFragment.this.getContext(), mResultsBitmap);

                    }
                });
                MainActivity.navController.navigate(R.id.nav_item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mAppExcutor = new AppExecutor();
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);
        mImageView = root.findViewById(R.id.image_view_preview);
        mNameInput = root.findViewById(R.id.name_input);
        mPriceInput = root.findViewById(R.id.price_input);

        root.findViewById(R.id.button_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    // Launch the camera if the permission exists
                    launchCamera();
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
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {
            // Otherwise, delete the temporary image file
//            BitmapUtils.deleteImageFile(getContext(), mTempPhotoPath);
            if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
                Uri fullPhotoUri = data.getData();
                mImageView.setImageURI(fullPhotoUri);
            }

        }
    }

    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(getContext());
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();


                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);
                Log.d("test", String.valueOf(photoURI));

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
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
}