package com.example.dvhplay.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.R;
import com.example.dvhplay.User.LoginActivity;
import com.example.dvhplay.databinding.FragmentMoreBinding;
import com.example.dvhplay.helper.VFMSharePreference;

public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    VFMSharePreference sharePreference;
    String username="";
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
        binding.tvListFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment child = new ListFavoriteFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_Fragment,child).commit();
                binding.llListMore.setVisibility(View.GONE);
            }
        });
        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setMessage(R.string.confirmLogout)
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharePreference.remove("password");
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
        });
        return binding.getRoot();
    }
}