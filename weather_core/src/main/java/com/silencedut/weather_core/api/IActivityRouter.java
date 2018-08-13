package com.silencedut.weather_core.api;

import com.silencedut.hub.IHubActivity;

/**
 * @author SilenceDut
 * @date 2018/8/13
 */
public interface IActivityRouter extends IHubActivity{
    void toAboutActivity();
    void toSearchActivity();
}
