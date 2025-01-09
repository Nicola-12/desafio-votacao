package com.challenge.votation.infra.utilities;

import java.util.Random;

public class CPFUtils
{
    public static boolean isCpfValid( String cpf )
    {
        Random random = new Random();
        
        return random.nextBoolean();
    }
}
