package com.cgvsu.gui;

import com.cgvsu.model.FinishedModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ModelsController {

    private FinishedModel currentModel;
    private final List<FinishedModel> modelList = new ArrayList<>();
    private final Set<String> namesSet = new HashSet<>();

    public Set<String> getNamesSet() {
        return namesSet;
    }

    public void setCurrent(String modelName) {
        for (FinishedModel model : modelList) {
            if (model.getName().equals(modelName)) {
                currentModel = model;
                return;
            }
        }
    }

    public FinishedModel getCurrent() {
        return currentModel;
    }

    public List<FinishedModel> getModels() {
        return modelList;
    }

    public void addNameToNameSet(String name) {
        namesSet.add(name);
    }

    public void addModel(FinishedModel finishedModel) {
        modelList.add(finishedModel);
        namesSet.add(finishedModel.getName());
    }
}
