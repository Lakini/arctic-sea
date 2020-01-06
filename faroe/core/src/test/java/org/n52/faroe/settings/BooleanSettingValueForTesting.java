/*
 * Copyright 2015-2020 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.faroe.settings;

import org.n52.faroe.SettingType;
import org.n52.faroe.SettingValue;

/**
 * @since 1.0.0
 *
 */
public class BooleanSettingValueForTesting implements SettingValue<Boolean> {

    private static final long serialVersionUID = 2591791402790665055L;

    private String key;

    private Boolean value;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public SettingType getType() {
        return SettingType.BOOLEAN;
    }

}
