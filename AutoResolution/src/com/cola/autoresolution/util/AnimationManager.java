package com.cola.autoresolution.util;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 动画管理类
 * 
 * @author
 * 
 */

public class AnimationManager {

	private class AnimationEntity {
		View view;
		Animation animation;
		AnimationListener listener;
	}

	private boolean mAnimPlayState = false;

	private List<AnimationEntity> mAnimEntityList;

	private static AnimationManager instance = null;

	private AnimationManager() {
		mAnimEntityList = new ArrayList<AnimationEntity>();
	}

	public static AnimationManager getInstance() {
		if (instance == null) {
			instance = new AnimationManager();
		}
		return instance;
	}

	public void insertAnimation(View view, Animation animation,
			AnimationListener listener) {
		if (animation == null) {
			return;
		}

		AnimationEntity entity = new AnimationEntity();
		entity.view = view;
		entity.animation = animation;
		entity.listener = listener;
		mAnimEntityList.add(entity);
		
		startAnimation();
	}

	public void clearAnimation() {
		if (mAnimPlayState && !mAnimEntityList.isEmpty()) {
			AnimationEntity entity = mAnimEntityList.get(0);
			if (entity != null && entity.view != null) {
				entity.view.clearAnimation();
			}
			mAnimEntityList.clear();
			mAnimPlayState = false;
		}
	}

	public void startAnimation() {
		if (mAnimPlayState || mAnimEntityList.isEmpty()) {
			return;
		}

		final AnimationEntity entity = mAnimEntityList.get(0);
		if (entity != null && entity.view != null && entity.animation != null) {
			entity.animation
					.setAnimationListener(new Animation.AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							if (entity.listener != null){
								entity.listener.onAnimationStart(animation);
							}
							mAnimPlayState = true;
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							if (entity.listener != null){
								entity.listener.onAnimationRepeat(animation);
							}
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							if (entity.listener != null){
								entity.listener.onAnimationEnd(animation);
							}
							mAnimPlayState = false;
							mAnimEntityList.remove(entity);
							startAnimation();
						}
					});
			entity.view.startAnimation(entity.animation);
		}
	}
}
