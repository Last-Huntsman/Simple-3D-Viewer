package com.cgvsu.gui;

import com.cgvsu.model.FinishedModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ModelsController {

    public FinishedModel currentModel;

    private final List<FinishedModel> modelList = new ArrayList<>();

    private final Set<String> namesSet = new HashSet<>();

    public Set<String> getNamesSet() {
        return namesSet;
    }

    public void setCurrent(int index) {
        if (index >= 0 && index < modelList.size())
            currentModel = modelList.get(index);
    }

    public void addNameToNameSet(String name) {
        namesSet.add(name);
    }

    public void addModel(FinishedModel finishedModel) {
        modelList.add(finishedModel);
    }
}
