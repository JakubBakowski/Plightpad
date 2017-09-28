package com.plightpad;

import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;

import com.ldoublem.loadingviewlib.view.LVBlock;
import com.plightpad.tools.BaseActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.login_email_edit_text)
    public TextInputLayout emailEditText;
    @BindView(R.id.login_password_edit_text)
    public TextInputLayout passwordEditText;
    @BindView(R.id.google_login_btn)
    public FancyButton googleButton;
    @BindView(R.id.facebook_login_button)
    public LoginButton facebookButton;
    @BindView(R.id.facebook_button_better)
    public FancyButton facebookBetterBtn;
    @BindView(R.id.twitter_login_button)
    public TwitterLoginButton twitterButton;
    @BindView(R.id.twitter_button_better)
    public FancyButton twitterBetterBtn;
    @BindView(R.id.custom_login_button)
    public FancyButton customLoginButton;
    @BindView(R.id.custom_register_button)
    public FancyButton customRegisterButton;
    @BindView(R.id.google_sign_in_official_button)
    SignInButton googleOriginalButton;


    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManager;
    public TwitterConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FIREBASE

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
//        TWITTER
        config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
//        FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
//        ACTIVITY
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeForm();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        mGoogleApiClient.connect();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        signOut();
    }

    //    ADDING CLICK LISTENERS AND CALLBACKS
    public void initializeForm() {
        googleOriginalButton.setOnClickListener(s -> signInGoogle());
        customRegisterButton.setOnClickListener(s -> createAccount(emailEditText.getEditText().getText().toString(), passwordEditText.getEditText().getText().toString()));
        customLoginButton.setOnClickListener(s -> signInCustomLogin(emailEditText.getEditText().getText().toString(), passwordEditText.getEditText().getText().toString()));
        googleButton.setOnClickListener(s -> {
            googleOriginalButton.performClick();
            signInGoogle();
        });
        facebookBetterBtn.setOnClickListener(s -> facebookButton.performClick());
        facebookButton.setReadPermissions("email", "public_profile", "user_friends");
        twitterBetterBtn.setOnClickListener(s -> twitterButton.performClick());
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                updateUI(null);
            }
        });
        //        FACEBOOK BUTTON
        mCallbackManager = CallbackManager.Factory.create();
        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
    }

    private void signOut() {
        mAuth.signOut(); //CUSTOM
        LoginManager.getInstance().logOut(); //FACEBOOK
        FirebaseAuth.getInstance().signOut(); //TWITTER
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback( //GOOGLE
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
        updateUI(null);
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.sending_email_verification_success) + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.sending_email_verification_error),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = emailEditText.getEditText().getText().toString();
        InternetAddress emailAddress;
        boolean isEmailCorrect = true;
        try {
            emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            isEmailCorrect = false;
        }
        if (TextUtils.isEmpty(email) && isEmailCorrect) {
            emailEditText.setError(getResources().getString(R.string.wrong_email));
            valid = false;
        } else {
            emailEditText.setError(null);
        }
        String password = passwordEditText.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getResources().getString(R.string.wrong_password));
            valid = false;
        } else {
            passwordEditText.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            startActivity(new Intent(this, MenuActivity.class));
            try { //if user has a name we display it with welcome message
                Toast.makeText(this, getResources().getString(R.string.welcome_user) + user.getDisplayName().toString(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException npe) {
                Toast.makeText(this, getResources().getString(R.string.successful_login), Toast.LENGTH_SHORT).show();
            }
        } else { //if user is null it means it was logout
            Toast.makeText(this, getResources().getString(R.string.successful_logout), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                updateUI(null);
            }
        } else {
            twitterButton.onActivityResult(requestCode, resultCode, data);
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    //    CUSTOM
    private void signInCustomLogin(String email, String password) {
        Log.d(TAG, "signInCustom:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.this.updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                            LoginActivity.this.updateUI(null);
                        }

                        // [END_EXCLUDE]
                    }
                });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.this.updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String taskException = task.getException().getClass().toString();
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.to_weak_password),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.authentication_failed),
                                        Toast.LENGTH_SHORT).show();
                            }
                            LoginActivity.this.updateUI(null);
                        }
                    }
                });
    }

    //AUTH WITH GOOGLE
    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    //AUTH WITH FACEBOOK
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_failed),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    //AUTH WITH TWITTER
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        showProgressDialog();
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_failed),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    @BindView(R.id.loading_login_activity)
    LVBlock loadingIndicator;

    @BindView(R.id.loading_relative_layout)
    RelativeLayout loadingLayout;

    private void showProgressDialog() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);
        loadingIndicator.setViewColor(getResources().getColor(R.color.thC));
        loadingIndicator.isShadow(true);
        loadingIndicator.setShadowColor(getResources().getColor(R.color.semi_transparent_black));
        loadingIndicator.startAnim();
    }

    private void hideProgressDialog() {
        loadingLayout.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}