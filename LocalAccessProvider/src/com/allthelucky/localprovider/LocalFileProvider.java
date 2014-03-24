package com.allthelucky.localprovider;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class LocalFileProvider extends ContentProvider {

	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
		final String path = uri.getPath();
		ParcelFileDescriptor parcel = ParcelFileDescriptor.open(new File(path), ParcelFileDescriptor.MODE_READ_ONLY);
		return parcel;
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public int delete(Uri uri, String s, String[] as) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

	@Override
	public String getType(Uri uri) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentvalues) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

	@Override
	public Cursor query(Uri uri, String[] as, String s, String[] as1, String s1) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

	@Override
	public int update(Uri uri, ContentValues contentvalues, String s, String[] as) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

}
