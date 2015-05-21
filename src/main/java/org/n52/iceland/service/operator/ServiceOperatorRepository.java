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
package org.n52.iceland.service.operator;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.n52.iceland.exception.ConfigurationException;
import org.n52.iceland.ogc.ows.OwsExceptionReport;
import org.n52.iceland.request.operator.RequestOperatorRepository;
import org.n52.iceland.util.repository.AbstractConfiguringServiceLoaderRepository;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.collections.MultiMaps;
import org.n52.iceland.util.collections.SetMultiMap;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 *
 * @since 4.0.0
 */
public class ServiceOperatorRepository extends AbstractConfiguringServiceLoaderRepository<ServiceOperator> {
    @Deprecated
    private static ServiceOperatorRepository instance;
    /**
     * Implemented ServiceOperator
     */
    private final Map<ServiceOperatorKey, ServiceOperator> serviceOperators = Maps.newHashMap();

    /** supported SOS versions */
    private final SetMultiMap<String, String> supportedVersions = MultiMaps.newSetMultiMap();

    /** supported services */
    private final Set<String> supportedServices = Sets.newHashSet();

    @Inject
    private RequestOperatorRepository requestOperatorRepository;

    /**
     * Load implemented request listener
     *
     * @throws ConfigurationException
     *             If no request listener is implemented
     */
    private ServiceOperatorRepository() throws ConfigurationException {
        super(ServiceOperator.class, false);
        load(false);
        ServiceOperatorRepository.instance = this;
    }

    @Deprecated
    public static ServiceOperatorRepository getInstance() {
        return ServiceOperatorRepository.instance;
    }

    /**
     * Load the implemented request listener and add them to a map with
     * operation name as key
     *
     * @param implementations
     *            the loaded implementations
     *
     * @throws ConfigurationException
     *             If no request listener is implemented
     */
    @Override
    protected void processConfiguredImplementations(final Set<ServiceOperator> implementations)
            throws ConfigurationException {
        serviceOperators.clear();
        supportedServices.clear();
        supportedVersions.clear();
        for (final ServiceOperator so : implementations) {
            serviceOperators.put(so.getServiceOperatorKey(), so);
            supportedVersions.add(so.getServiceOperatorKey().getService(), so.getServiceOperatorKey()
                    .getVersion());
            supportedServices.add(so.getServiceOperatorKey().getService());
        }
    }

    /**
     * Update/reload the implemented request listener
     *
     * @throws ConfigurationException
     *             If no request listener is implemented
     */
    @Override
    public void update() throws ConfigurationException {
        this.requestOperatorRepository.update();
        super.update();
    }

    /**
     * @return the implemented request listener
     */
    public Map<ServiceOperatorKey, ServiceOperator> getServiceOperators() {
        return Collections.unmodifiableMap(serviceOperators);
    }

    public Set<ServiceOperatorKey> getServiceOperatorKeyTypes() {
        return getServiceOperators().keySet();
    }

    public ServiceOperator getServiceOperator(final ServiceOperatorKey sok) {
        return serviceOperators.get(sok);
    }

    /**
     * @param service
     *            the service
     * @param version
     *            the version
     * @return the implemented request listener
     *
     *
     * @throws OwsExceptionReport
     */
    public ServiceOperator getServiceOperator(final String service, final String version) throws OwsExceptionReport {
        return getServiceOperator(new ServiceOperatorKey(service, version));
    }

    public Set<String> getAllSupportedVersions() {
        return CollectionHelper.union(supportedVersions.values());
    }

    /**
     * @param service
     *            the service
     * @return the supportedVersions
     *
     */
    public Set<String> getSupportedVersions(final String service) {
        if (isServiceSupported(service)) {
            return Collections.unmodifiableSet(supportedVersions.get(service));
        }
        return Sets.newHashSet();
    }

    /**
     * @param service
     *            the service
     * @param version
     *            the version
     * @return the supportedVersions
     *
     */
    public boolean isVersionSupported(final String service, final String version) {
        return isServiceSupported(service) && supportedVersions.get(service).contains(version);
    }

    /**
     * @return the supportedVersions
     */
    public Set<String> getSupportedServices() {
        return Collections.unmodifiableSet(supportedServices);
    }

    public boolean isServiceSupported(final String service) {
        return supportedServices.contains(service);
    }

}
