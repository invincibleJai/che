/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.api.machine;

import org.eclipse.che.api.core.model.workspace.runtime.Machine;
import org.eclipse.che.api.machine.shared.Constants;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.util.loging.Log;

import javax.validation.constraints.NotNull;

/**
 * Describe development machine instance.
 * Must contains all information that need to communicate with dev machine such as links, type, environment variable and etc.
 *
 * @author Vitalii Parfonov
 * @deprecated use {@link org.eclipse.che.ide.api.workspace.model.RuntimeImpl#getDevMachine()}
 */
@Deprecated
public class DevMachine extends MachineEntityImpl {

    public DevMachine(String name, @NotNull Machine devMachineDescriptor) {
        super(name, devMachineDescriptor);
    }

    @Deprecated
    public String getWsAgentWebSocketUrl() {
        return getWsAgentBaseUrl().replaceFirst("http", "ws") + "/ws";
    }

    /**
     * @return return base URL to the ws agent REST services. URL will be always without trailing slash
     * @deprecated use {@link AppContext#getDevAgentEndpoint()}
     */
    @Deprecated
    public String getWsAgentBaseUrl() {
        MachineServer server = getServer(Constants.WSAGENT_REFERENCE);
        if (server != null) {
            String url = server.getUrl();
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }

            // TODO (spi ide): remove path when it comes with URL
            return url + "/api";
        }

        //should not be
        String message = "Reference " + Constants.WSAGENT_REFERENCE + " not found in DevMachine description";
        Log.error(getClass(), message);
        throw new RuntimeException(message);
    }
}
