package com.silencedut.knowweather.citys.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;

/**
 * Created by SilenceDut on 16/10/24 .
 */

public class AddData implements BaseAdapterData {
    @Override
    public int getItemViewType() {
        return R.layout.item_add_city;
    }
}
