package com.example.dvhplay.more;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dvhplay.MainActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.User.LoginActivity;
import com.example.dvhplay.databinding.FragmentMoreBinding;
import com.example.dvhplay.helper.ScaleTouchListener;
import com.example.dvhplay.helper.VFMSharePreference;

import java.util.Locale;

public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    VFMSharePreference sharePreference;
    String username="";
    ScaleTouchListener favorieListner, languageListner, logouListner;
    ScaleTouchListener.Config config;
    public MoreFragment() {
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_more, container, false);
        sharePreference = new VFMSharePreference(getActivity().getBaseContext());
        username = sharePreference.getStringValue("username");
        binding.tvUserName.setText(username);
        setupViews();
        return binding.getRoot();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setLocale(Context context, String langCode) {
        Locale locale;
        locale = new Locale(langCode);
        Configuration config = new Configuration(context.getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);

        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
    public void setupViews() {
        config = new ScaleTouchListener.Config(100,0.90f,0.5f);
        favorieListner = new ScaleTouchListener(config){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                Fragment child = new ListFavoriteFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_Fragment,child).commit();
                binding.llListMore.setVisibility(View.GONE);
            }
        };
        languageListner = new ScaleTouchListener(config){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.language_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.mnEnglish:
                                setLocale(getContext(),"en");
                                startActivity(new Intent(getContext(), MainActivity.class));
                                break;
                            case R.id.mnVietNam:
                                setLocale(getContext(),"vi");
                                startActivity(new Intent(getContext(), MainActivity.class));
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        };
        logouListner = new ScaleTouchListener(config){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setMessage(R.string.confirmLogout)
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharePreference.remove("password");
                                getActivity().finish();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            }
                        })
                        .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorButtonDialog));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorButtonDialog));
                    }
                });
                alertDialog.show();

            }
        };
        binding.tvListFavorite.setOnTouchListener(favorieListner);
        binding.tvLanguage.setOnTouchListener(languageListner);
        binding.tvLogout.setOnTouchListener(logouListner);
    }
}