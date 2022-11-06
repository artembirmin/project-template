/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogUtils {
    private static final String SINGLE_INSTANCE_TAG_PREFIX = "SINGLE_INSTANCE_";

    private DialogUtils() {
        throw new AssertionError("Instantiating utility class.");
    }

    public static <T extends DialogFragment> void showSingle(FragmentManager fragmentManager,
                                                             T dialog, String tag) {
        String SINGLE_INSTANCE_TAG = createTag(tag);
        if (findFragment(fragmentManager, tag) == null) {
            dialog.show(fragmentManager, SINGLE_INSTANCE_TAG);
        }
    }

    @NonNull
    private static String createTag(String tag) {
        return SINGLE_INSTANCE_TAG_PREFIX + tag;
    }

    public static <T extends DialogFragment> void showSingle(FragmentManager fragmentManager,
                                                             T dialog) {
        showSingle(fragmentManager, dialog, getTagFromClass(dialog.getClass()));
    }

    public static <T extends DialogFragment> void hideDialog(FragmentManager fragmentManager,
                                                             String tag) {
        T dialog = findFragment(fragmentManager, tag);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static <T extends DialogFragment> void hideDialog(FragmentManager fragmentManager,
                                                             Class<? extends DialogFragment> cls) {
        T dialog = findFragment(fragmentManager, getTagFromClass(cls));
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static <T extends DialogFragment> T findFragment(FragmentManager fragmentManager,
                                                            Class<T> clazz) {
        //noinspection unchecked
        return (T) fragmentManager.findFragmentByTag(createTag(getTagFromClass(clazz)));
    }

    public static <T extends DialogFragment> T findFragment(FragmentManager fragmentManager,
                                                            String tag) {
        //noinspection unchecked
        return (T) fragmentManager.findFragmentByTag(createTag(tag));
    }

    private static String getTagFromClass(Class<? extends DialogFragment> clazz) {
        return clazz.getSimpleName();
    }
}
