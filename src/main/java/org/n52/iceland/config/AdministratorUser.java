/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
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
package org.n52.iceland.config;

/**
 * Interface for users that are allowed to administer the service.
 * Implementations are {@link SettingsService} specific.
 *
 * @author Christian Autermann <c.autermann@52north.org>
 * @since 4.0.0
 */
public interface AdministratorUser {

    /**
     * Get the value of password.
     *
     * @return the value of password
     */
    String getPassword();

    /**
     * Get the value of username.
     *
     * @return the value of username
     */
    String getUsername();

    /**
     * Set the value of password.
     *
     * @param password new value of password
     *
     * @return {@code this}
     */
    AdministratorUser setPassword(String password);

    /**
     * Set the value of username.
     *
     * @param username new value of username
     *
     * @return {@code this}
     */
    AdministratorUser setUsername(String username);
}
