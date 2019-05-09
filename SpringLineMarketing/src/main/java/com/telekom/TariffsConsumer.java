package com.telekom;


import org.slf4j.Logger;

import javax.annotation.ManagedBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@ApplicationScoped @Named
public class TariffsConsumer {
    private ArrayList<TariffPromoted> tariffsList = new ArrayList<TariffPromoted>();

    @Inject
    private Logger logger;

    @PostConstruct
    public void load() {
        getTariffs();
    }


    void getTariffs() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/springLine/promotedTariffs");
        JsonArray response = target.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
        tariffsList.clear();
        for (JsonValue t : response) {
            JsonObject tmp = t.asJsonObject();
            tariffsList.add(new TariffPromoted(tmp.getString("name"), tmp.getJsonNumber("price").doubleValue(), tmp.getString("description")));
        }
    }

    public ArrayList<TariffPromoted> getTariffsList() {
        return tariffsList;
    }
}
