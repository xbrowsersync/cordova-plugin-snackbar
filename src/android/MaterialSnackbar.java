package com.materialSnackbar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.TextView;

public class MaterialSnackbar extends CordovaPlugin {
    private FrameLayout layout;
    private Snackbar snackbar;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        layout = (FrameLayout) webView.getView().getParent();
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        try {
            switch (action) {
                case "show":
                    show(args, callbackContext);
                    return true;
                case "hide":
                    hide(callbackContext);
                    return true;
            }
        }
        catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
        }

        return false;
    }

    private void hide(CallbackContext callbackContext) {
        snackbar.dismiss();
        callbackContext.success();
    }

    private void show(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (snackbar != null){
            snackbar.dismiss();
        }

        JSONObject arg_object = args.getJSONObject(0);
        final String text = arg_object.getString("text");
        final int duration = arg_object.getInt("duration");
        final String bgColor = arg_object.getString("bgColor");
        final String textColor = arg_object.getString("textColor");
        final int maxLines = arg_object.getInt("maxLines");
        final String button = arg_object.getString("button");

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);

                // Set duration
                snackbar.setDuration(duration > 0 ? duration : Snackbar.LENGTH_LONG);

                // Set background color
                if (bgColor != null && !bgColor.isEmpty()) {
                    int colorBackground = Color.parseColor(bgColor);
                    snackbarView.setBackgroundColor(colorBackground);
                }

                // Set text color
                if (textColor != null && !textColor.isEmpty()) {
                    int colorText = Color.parseColor(textColor);
                    snackbar.setActionTextColor(colorText);
                    snackbarTextView.setTextColor(colorText);
                }

                // Set max lines
                if (maxLines > 0) {
                    snackbarTextView.setMaxLines(maxLines);
                }

                if (button != null && !button.isEmpty()){
                    snackbar.setAction(button, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            callbackContext.success("clicked");
                        }
                    });
                }

                snackbar.show();
            }
        });
    }
}
