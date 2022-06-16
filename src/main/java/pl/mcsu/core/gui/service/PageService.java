package pl.mcsu.core.gui.service;

import pl.mcsu.core.gui.Action;
import pl.mcsu.core.gui.GUI;
import pl.mcsu.core.gui.model.Button;

public interface PageService {

    Button builder(Action action, GUI gui);

}
