package com.game.clubs.stumps.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.game.clubs.stumps.BaseActivity;
import com.game.clubs.stumps.R;
import com.game.clubs.stumps.databinding.ActivityLoginBinding;

/**
 * Created by Praneeth on 10/10/2018.
 */

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
