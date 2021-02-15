package edu.neu.madcourse.numad21s_archita_sundaray;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextAppName;
    private EditText editTextUrl;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String appName = editTextAppName.getText().toString();
                        String url = editTextUrl.getText().toString();
                        //validation of url:
                        String[] urlParts = url.split("\\.");
                        System.out.println(urlParts);
                        if (urlParts[0].equals("https://www")) {
                            listener.applyTexts(appName, url);
                        } else {
                            listener.applyTexts("Default", "https://www.google.com");
                        }
                    }
                });
        editTextAppName = view.findViewById(R.id.edit_appname);
        editTextUrl = view.findViewById(R.id.edit_url);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String appName, String url);
    }
}
