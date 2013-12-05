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
 * Apr 15, 2011
 */
package pt.lsts.neptus.plugins.mavs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import pt.lsts.neptus.console.ConsoleLayout;
import pt.lsts.neptus.plugins.PluginDescription;
import pt.lsts.neptus.plugins.PluginUtils;
import pt.lsts.neptus.plugins.SimpleSubPanel;
import pt.lsts.neptus.types.mission.plan.PlanType;
import pt.lsts.neptus.util.FileUtil;
import pt.lsts.neptus.util.GuiUtils;
import pt.lsts.neptus.util.ImageUtils;

/**
 * @author zp
 *
 */
@PluginDescription(name="QGroundControl interface")
public class QGCExporter extends SimpleSubPanel {

    /**
     * @param console
     */
    public QGCExporter(ConsoleLayout console) {
        super(console);
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void initSubPanel() {
        addMenuItem("Tools>QGC>Export Waypoint List", ImageUtils.getIcon(PluginUtils.getPluginIcon(getClass())), new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlanType plan = getConsole().getPlan();
                    if (plan == null)
                        throw new Exception("Please choose a main plan first");
                    String wptList = WaypointUtils.getAsQGCFormat(plan);
                    JFileChooser fchooser = new JFileChooser();
                    fchooser.setAcceptAllFileFilterUsed(true);
                    fchooser.setMultiSelectionEnabled(false);
                    int option = fchooser.showSaveDialog(getConsole());
                    if (option != JFileChooser.APPROVE_OPTION)
                        return;
                    File f = fchooser.getSelectedFile();
                    FileUtil.saveToFile(f.getAbsolutePath(), wptList);
                    GuiUtils.infoMessage(getConsole(), "Export Waypoint List", "Waypoint list successfully exported to '"+f.getName()+"'");
                }
                catch (Exception ex) {
                    GuiUtils.errorMessage(getConsole(), ex);
                }
                
            }
        });
    }

    /* (non-Javadoc)
     * @see pt.lsts.neptus.plugins.SimpleSubPanel#cleanSubPanel()
     */
    @Override
    public void cleanSubPanel() {
        // TODO Auto-generated method stub
        
    }
}