/*
 * Copyright (C) 2011 Patrik Akerfeldt
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.strike.downba_app.view;

import android.support.v4.view.ViewPager;

/**
 * A PageIndicator is responsible to show an visual indicator on the total views
 * number and the current visible view.
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener {

    /**
     * Bind the indicator to a ViewPager.
     * 
     * @param view
     *            the new view pager
     */
    void setViewPager(ViewPager view);

    /**
     * Bind the indicator to a ViewPager.
     * 
     * @param view
     *            the view
     * @param initialPosition
     *            the initial position
     * @version
     */
    void setViewPager(ViewPager view, int initialPosition);

    /**
     * Sets the current item.
     * 
     * @param item
     *            the new current item
     */
    void setCurrentItem(int item);

    /**
     * Set a page change listener which will receive forwarded events.
     * 
     * @param listener
     *            the new on page change listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * Notify the indicator that the fragment list has changed.
     * 
     * @version
     */
    void notifyDataSetChanged();
}
