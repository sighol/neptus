/*
 * Copyright (c) 2004-2015 Universidade do Porto - Faculdade de Engenharia
 * Laboratório de Sistemas e Tecnologia Subaquática (LSTS)
 * All rights reserved.
 * Rua Dr. Roberto Frias s/n, sala I203, 4200-465 Porto, Portugal
 *
 * This file is part of Neptus, Command and Control Framework.
 *
 * Commercial Licence Usage
 * Licencees holding valid commercial Neptus licences may use this file
 * in accordance with the commercial licence agreement provided with the
 * Software or, alternatively, in accordance with the terms contained in a
 * written agreement between you and Universidade do Porto. For licensing
 * terms, conditions, and further information contact lsts@fe.up.pt.
 *
 * European Union Public Licence - EUPL v.1.1 Usage
 * Alternatively, this file may be used under the terms of the EUPL,
 * Version 1.1 only (the "Licence"), appearing in the file LICENSE.md
 * included in the packaging of this file. You may not use this work
 * except in compliance with the Licence. Unless required by applicable
 * law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations at
 * https://www.lsts.pt/neptus/licence.
 *
 * For more information please see <http://lsts.fe.up.pt/neptus>.
 *
 * Author: tsmarques
 * 25 Mar 2015
 */
package pt.lsts.neptus.plugins.preflight.check.automated;

import pt.lsts.neptus.plugins.preflight.Preflight;
import pt.lsts.neptus.plugins.preflight.check.AutomatedCheck;
import pt.lsts.neptus.plugins.update.PeriodicUpdatesService;
import pt.lsts.neptus.types.mission.plan.PlanType;

@SuppressWarnings("serial")
public class CheckLostComms extends AutomatedCheck {
    public CheckLostComms(boolean maintainState) {
        super("Lost Comms", "Planning", maintainState);
    }

    @Override
    public long millisBetweenUpdates() {
        return 2000;
    }

    
    /* TODO: Check lost comms in the vehicle, not just the console */
    @Override
    public boolean update() {
        PlanType lostComms = Preflight.CONSOLE.
                getMission().
                    getIndividualPlansList().
                        get("lost_comms");
        try {
            if(lostComms.validatePlan())
                setState(VALIDATED);
        }
        catch(Exception e) {
            setState(NOT_VALIDATED);
        }
        return true;
    }
}