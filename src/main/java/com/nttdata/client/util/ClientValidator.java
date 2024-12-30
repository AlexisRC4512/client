package com.nttdata.client.util;

import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public class ClientValidator {
    private static final EnumMap<TypeClient, Set<SubTypeClient>> listSubTypeClient = new EnumMap<>(TypeClient.class);
    static {
        listSubTypeClient.put(TypeClient.PERSONAL, EnumSet.of(SubTypeClient.VIP, SubTypeClient.NORMAL));
        listSubTypeClient.put(TypeClient.BUSINESS, EnumSet.of(SubTypeClient.PYME, SubTypeClient.NORMAL));
    }

    public static boolean isValidSubtipo(TypeClient type, SubTypeClient subtipo) {
        return listSubTypeClient.getOrDefault(type, EnumSet.noneOf(SubTypeClient.class)).contains(subtipo);
    }
}
