package com.ender.mapmodule.map;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ender.mapmodule.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Ender Erol.
 */
public abstract class ContainerMapFragment extends InfoWindowMapFragment {

    private ImageView ivSwitch;
    private RelativeLayout rlContainer;

    private int screenHeight;

    @Override
    protected void setViews(View view) {
        super.setViews(view);

        ivSwitch = (ImageView) view.findViewById(R.id.ivSwitch);
        rlContainer = (RelativeLayout) view.findViewById(R.id.rlContainer);

        ivSwitch.setImageResource(getMapIcon());

        setContainerHeight();

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlContainer.getVisibility() == View.VISIBLE) {
                    hideContainer();
                } else {
                    showContainer();
                }
            }
        });

        ivSwitch.setVisibility(View.VISIBLE);
        ivSwitch.performClick();

    }

    @Override
    protected int getLayout() {
        return R.layout.info_window_map_container_fragment;
    }

    @Override
    public GoogleMap.OnInfoWindowClickListener setOnInfoWindowClickListener() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.showInfoWindow();
            }
        };
    }

    private void setContainerHeight() {
        //Screen height
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;

        //Container height
        ViewGroup.LayoutParams layoutParams = rlContainer.getLayoutParams();
        layoutParams.height = (int) ((screenHeight * 0.9) / 2);

    }

    protected void hideContainer() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_map_container_hide);
        rlContainer.startAnimation(animation);
        rlContainer.setVisibility(View.GONE);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivSwitch, "translationY", ivSwitch.getTranslationY(),
                (float) (ivSwitch.getTranslationY() + ((screenHeight * 0.87) / 2)));

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivSwitch.setImageResource(getListIcon());
                ivSwitch.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivSwitch.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                /* empty */
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                /* empty */
            }
        });

        objectAnimator.setDuration(1000).start();
    }

    protected void showContainer() {
        Animation showAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_map_container_show);
        rlContainer.setVisibility(View.VISIBLE);
        rlContainer.startAnimation(showAnimation);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivSwitch, "translationY", ivSwitch.getTranslationY(),
                (float) (ivSwitch.getTranslationY() - ((screenHeight * 0.87) / 2)));

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivSwitch.setImageResource(getMapIcon());
                ivSwitch.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivSwitch.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                /* empty */
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                /* empty */
            }
        });

        objectAnimator.setDuration(1000).start();

    }

    public abstract int getMapIcon();

    public abstract int getListIcon();

}