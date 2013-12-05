/*
 * Copyright (c) 2004-2013 Universidade do Porto - Faculdade de Engenharia
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
 * Version 1.1 only (the "Licence"), appearing in the file LICENCE.md
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
 * Author: José Pinto
 * 12 de Ago de 2011
 */
package pt.lsts.neptus.planeditor;

import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import pt.lsts.neptus.mp.Maneuver;
import pt.lsts.neptus.types.mission.TransitionType;
import pt.lsts.neptus.types.mission.plan.PlanType;

/**
 * @author ZP
 *
 */
public class ManeuverAddEdit extends AbstractUndoableEdit {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected PlanType plan;
    protected Maneuver added;
    protected Vector<TransitionType> transitions = new Vector<TransitionType>();
    public ManeuverAddEdit(PlanType plan, Maneuver man) {
        this.plan = plan;
        this.added = man;
        transitions.addAll(plan.getGraph().getExitingTransitions(man));
        transitions.addAll(plan.getGraph().getIncomingTransitions(man));
    }
    
    @Override
    public boolean canRedo() {
        return true;
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
    
    @Override
    public void redo() throws CannotRedoException {
        if (plan.getGraph().getManeuver(added.getId()) == null) {
            plan.getGraph().addManeuver((Maneuver)added.clone());  
            for (TransitionType t : transitions)
                plan.getGraph().getTransitions().put(t.getId(), t);            
        }
        else
            throw new CannotRedoException();
    }
    
    @Override
    public void undo() throws CannotUndoException {
        plan.getGraph().removeManeuver(plan.getGraph().getManeuver(added.getId()));
    }
}