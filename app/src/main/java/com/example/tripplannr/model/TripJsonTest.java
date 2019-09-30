package com.example.tripplannr.model;

public class TripJsonTest {

    public static String TRIPS = "{\n" +
            "  \"TripList\": {\n" +
            "    \"noNamespaceSchemaLocation\": \"http://api.vasttrafik.se/v1/hafasRestTrip.xsd\",\n" +
            "    \"servertime\": \"08:34\",\n" +
            "    \"serverdate\": \"2019-09-24\",\n" +
            "    \"Trip\": [\n" +
            "      {\n" +
            "        \"alternative\": \"true\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Buss 16\",\n" +
            "            \"sname\": \"16\",\n" +
            "            \"journeyNumber\": \"60\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014501600060\",\n" +
            "            \"direction\": \"Eketrägatan\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960003\",\n" +
            "              \"routeIdx\": \"5\",\n" +
            "              \"time\": \"08:33\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:36\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"routeIdx\": \"15\",\n" +
            "              \"time\": \"08:55\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:56\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=328893%2F129755%2F266856%2F23805%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"time\": \"08:55\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"time\": \"09:02\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"alternative\": \"true\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 7\",\n" +
            "            \"sname\": \"7\",\n" +
            "            \"journeyNumber\": \"59\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014500700059\",\n" +
            "            \"direction\": \"Bergsjön\",\n" +
            "            \"fgColor\": \"#7d4313\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960001\",\n" +
            "              \"routeIdx\": \"14\",\n" +
            "              \"time\": \"08:36\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:37\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Kungsportsplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004090001\",\n" +
            "              \"routeIdx\": \"18\",\n" +
            "              \"time\": \"08:44\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:43\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=27486%2F14012%2F19872%2F777%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Kungsportsplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004090001\",\n" +
            "              \"time\": \"08:48\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Kungsportsplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004090003\",\n" +
            "              \"time\": \"08:48\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Buss 50\",\n" +
            "            \"sname\": \"50\",\n" +
            "            \"journeyNumber\": \"64\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014505000064\",\n" +
            "            \"direction\": \"Frölunda Torg\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Kungsportsplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004090003\",\n" +
            "              \"routeIdx\": \"11\",\n" +
            "              \"time\": \"08:52\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:52\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242004\",\n" +
            "              \"routeIdx\": \"13\",\n" +
            "              \"time\": \"08:57\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"rtTime\": \"08:57\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=907752%2F305587%2F315302%2F144934%2F80%3Fdate%3D2019-09-24%26station_evaId%3D4090003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242004\",\n" +
            "              \"time\": \"09:02\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"time\": \"09:02\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"286 Älvsnabbare\",\n" +
            "            \"sname\": \"286\",\n" +
            "            \"journeyNumber\": \"22\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528600022\",\n" +
            "            \"direction\": \"Lindholmspiren, Fri resa\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"routeIdx\": \"0\",\n" +
            "              \"time\": \"09:04\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"rtTime\": \"09:04\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"09:10\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:10\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Notes\": {\n" +
            "              \"Note\": {\n" +
            "                \"key\": \"other\",\n" +
            "                \"severity\": \"low\",\n" +
            "                \"priority\": \"1\",\n" +
            "                \"$\": \"Färjan är avgiftsfri.\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=812958%2F281964%2F42128%2F249942%2F80%3Fdate%3D2019-09-24%26station_evaId%3D6242005%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"alternative\": \"true\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 10\",\n" +
            "            \"sname\": \"10\",\n" +
            "            \"journeyNumber\": \"37\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014501000037\",\n" +
            "            \"direction\": \"Biskopsgården\",\n" +
            "            \"fgColor\": \"#b4e16e\",\n" +
            "            \"bgColor\": \"#0e629b\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960001\",\n" +
            "              \"routeIdx\": \"3\",\n" +
            "              \"time\": \"08:36\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:37\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lilla Bommen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004380002\",\n" +
            "              \"routeIdx\": \"9\",\n" +
            "              \"time\": \"08:46\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"08:47\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=256488%2F90917%2F770078%2F299546%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lilla Bommen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004380002\",\n" +
            "              \"time\": \"08:52\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lilla Bommens Hamn, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004381001\",\n" +
            "              \"time\": \"08:57\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"285 Älvsnabben\",\n" +
            "            \"sname\": \"285\",\n" +
            "            \"journeyNumber\": \"16\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528500016\",\n" +
            "            \"direction\": \"Klippan via Lindholmspiren\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lilla Bommens Hamn, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004381001\",\n" +
            "              \"routeIdx\": \"0\",\n" +
            "              \"time\": \"09:00\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:00\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"2\",\n" +
            "              \"time\": \"09:12\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:12\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=95550%2F32705%2F825898%2F381099%2F80%3Fdate%3D2019-09-24%26station_evaId%3D4381001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"valid\": \"false\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Buss 16\",\n" +
            "            \"sname\": \"16\",\n" +
            "            \"journeyNumber\": \"62\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014501600062\",\n" +
            "            \"direction\": \"Eketrägatan\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960003\",\n" +
            "              \"routeIdx\": \"6\",\n" +
            "              \"time\": \"08:38\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:42\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760004\",\n" +
            "              \"routeIdx\": \"11\",\n" +
            "              \"time\": \"08:48\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"rtTime\": \"08:52\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=653028%2F237796%2F528222%2F46438%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760004\",\n" +
            "              \"time\": \"08:53\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"time\": \"08:53\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Buss 60\",\n" +
            "            \"sname\": \"60\",\n" +
            "            \"journeyNumber\": \"73\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014506000073\",\n" +
            "            \"reachable\": \"false\",\n" +
            "            \"direction\": \"Masthugget\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"routeIdx\": \"12\",\n" +
            "              \"time\": \"08:54\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:53\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"routeIdx\": \"13\",\n" +
            "              \"time\": \"08:57\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"08:56\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=257649%2F88102%2F321508%2F74872%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1760003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"time\": \"09:02\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"time\": \"09:02\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"286 Älvsnabbare\",\n" +
            "            \"sname\": \"286\",\n" +
            "            \"journeyNumber\": \"22\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528600022\",\n" +
            "            \"direction\": \"Lindholmspiren, Fri resa\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"routeIdx\": \"0\",\n" +
            "              \"time\": \"09:04\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"rtTime\": \"09:04\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"09:10\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:10\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Notes\": {\n" +
            "              \"Note\": {\n" +
            "                \"key\": \"other\",\n" +
            "                \"severity\": \"low\",\n" +
            "                \"priority\": \"1\",\n" +
            "                \"$\": \"Färjan är avgiftsfri.\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=585897%2F206277%2F113638%2F138500%2F80%3Fdate%3D2019-09-24%26station_evaId%3D6242005%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Buss 16\",\n" +
            "            \"sname\": \"16\",\n" +
            "            \"journeyNumber\": \"62\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014501600062\",\n" +
            "            \"direction\": \"Eketrägatan\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960003\",\n" +
            "              \"routeIdx\": \"6\",\n" +
            "              \"time\": \"08:38\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:42\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"routeIdx\": \"16\",\n" +
            "              \"time\": \"09:00\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"09:04\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=790926%2F283762%2F858050%2F165386%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"time\": \"09:00\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"time\": \"09:07\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"valid\": \"false\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 6\",\n" +
            "            \"sname\": \"6\",\n" +
            "            \"journeyNumber\": \"68\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014500600068\",\n" +
            "            \"direction\": \"Länsmansgården via Sahlgrenska\",\n" +
            "            \"fgColor\": \"#fa8719\",\n" +
            "            \"bgColor\": \"#00394d\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960002\",\n" +
            "              \"routeIdx\": \"18\",\n" +
            "              \"time\": \"08:40\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"08:44\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Linnéplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004510001\",\n" +
            "              \"routeIdx\": \"22\",\n" +
            "              \"time\": \"08:47\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:51\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=473559%2F162562%2F48398%2F133654%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960002%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 1\",\n" +
            "            \"sname\": \"1\",\n" +
            "            \"journeyNumber\": \"53\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014500100053\",\n" +
            "            \"direction\": \"Östra Sjukhuset\",\n" +
            "            \"fgColor\": \"#ffffff\",\n" +
            "            \"bgColor\": \"#00394d\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Linnéplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004510001\",\n" +
            "              \"routeIdx\": \"11\",\n" +
            "              \"time\": \"08:53\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:57\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242001\",\n" +
            "              \"routeIdx\": \"15\",\n" +
            "              \"time\": \"09:00\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:04\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=749676%2F253362%2F905496%2F202857%2F80%3Fdate%3D2019-09-24%26station_evaId%3D4510001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242001\",\n" +
            "              \"time\": \"09:05\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"time\": \"09:05\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"285 Älvsnabben\",\n" +
            "            \"sname\": \"285\",\n" +
            "            \"journeyNumber\": \"16\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528500016\",\n" +
            "            \"reachable\": \"false\",\n" +
            "            \"direction\": \"Klippan via Lindholmspiren\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"09:06\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"rtTime\": \"09:06\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"2\",\n" +
            "              \"time\": \"09:12\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:12\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=29433%2F10666%2F408382%2F194380%2F80%3Fdate%3D2019-09-24%26station_evaId%3D6242005%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Buss 16\",\n" +
            "            \"sname\": \"16\",\n" +
            "            \"journeyNumber\": \"64\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014501600064\",\n" +
            "            \"direction\": \"Eketrägatan\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960003\",\n" +
            "              \"routeIdx\": \"5\",\n" +
            "              \"time\": \"08:43\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"08:43\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"routeIdx\": \"15\",\n" +
            "              \"time\": \"09:05\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"09:05\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=754545%2F271639%2F815890%2F156439%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490003\",\n" +
            "              \"time\": \"09:05\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"time\": \"09:12\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960003\",\n" +
            "              \"time\": \"08:44\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Chalmersplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001961001\",\n" +
            "              \"time\": \"08:46\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Buss 55\",\n" +
            "            \"sname\": \"55\",\n" +
            "            \"journeyNumber\": \"28\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014505500028\",\n" +
            "            \"direction\": \"Lindholmen, Science Park\",\n" +
            "            \"fgColor\": \"#EBF4D5\",\n" +
            "            \"bgColor\": \"#2EB349\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmersplatsen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001961001\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"08:46\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:46\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490004\",\n" +
            "              \"routeIdx\": \"11\",\n" +
            "              \"time\": \"09:08\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"rtTime\": \"09:09\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=238200%2F88339%2F278386%2F59793%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1961001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Lindholmen, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004490004\",\n" +
            "              \"time\": \"09:08\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"D\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"time\": \"09:15\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"alternative\": \"true\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 7\",\n" +
            "            \"sname\": \"7\",\n" +
            "            \"journeyNumber\": \"61\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014500700061\",\n" +
            "            \"direction\": \"Bergsjön\",\n" +
            "            \"fgColor\": \"#7d4313\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960001\",\n" +
            "              \"routeIdx\": \"14\",\n" +
            "              \"time\": \"08:44\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:45\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760006\",\n" +
            "              \"routeIdx\": \"19\",\n" +
            "              \"time\": \"08:54\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"F\",\n" +
            "              \"rtTime\": \"08:54\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=188109%2F67553%2F350510%2F112556%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760006\",\n" +
            "              \"time\": \"08:59\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"F\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"time\": \"08:59\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Buss 60\",\n" +
            "            \"sname\": \"60\",\n" +
            "            \"journeyNumber\": \"75\",\n" +
            "            \"type\": \"BUS\",\n" +
            "            \"id\": \"9015014506000075\",\n" +
            "            \"direction\": \"Masthugget\",\n" +
            "            \"fgColor\": \"#00b6f1\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"routeIdx\": \"12\",\n" +
            "              \"time\": \"09:00\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"09:00\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"routeIdx\": \"13\",\n" +
            "              \"time\": \"09:03\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"09:03\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=449871%2F152178%2F286792%2F6561%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1760003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"time\": \"09:08\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"time\": \"09:08\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"286 Älvsnabbare\",\n" +
            "            \"sname\": \"286\",\n" +
            "            \"journeyNumber\": \"24\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528600024\",\n" +
            "            \"direction\": \"Lindholmspiren, Fri resa\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"routeIdx\": \"0\",\n" +
            "              \"time\": \"09:10\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"rtTime\": \"09:10\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"09:16\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:16\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Notes\": {\n" +
            "              \"Note\": {\n" +
            "                \"key\": \"other\",\n" +
            "                \"severity\": \"low\",\n" +
            "                \"priority\": \"1\",\n" +
            "                \"$\": \"Färjan är avgiftsfri.\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=437388%2F156774%2F100154%2F95740%2F80%3Fdate%3D2019-09-24%26station_evaId%3D6242005%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"valid\": \"false\",\n" +
            "        \"Leg\": [\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 10\",\n" +
            "            \"sname\": \"10\",\n" +
            "            \"journeyNumber\": \"39\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014501000039\",\n" +
            "            \"direction\": \"Biskopsgården\",\n" +
            "            \"fgColor\": \"#b4e16e\",\n" +
            "            \"bgColor\": \"#0e629b\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Chalmers, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001960001\",\n" +
            "              \"routeIdx\": \"3\",\n" +
            "              \"time\": \"08:46\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"08:46\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760002\",\n" +
            "              \"routeIdx\": \"8\",\n" +
            "              \"time\": \"08:56\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"08:56\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=581886%2F199383%2F228470%2F79731%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1960001%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760002\",\n" +
            "              \"time\": \"09:01\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"time\": \"09:01\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Spårvagn 9\",\n" +
            "            \"sname\": \"9\",\n" +
            "            \"journeyNumber\": \"52\",\n" +
            "            \"type\": \"TRAM\",\n" +
            "            \"id\": \"9015014500900052\",\n" +
            "            \"direction\": \"Kungssten\",\n" +
            "            \"fgColor\": \"#b9e2f8\",\n" +
            "            \"bgColor\": \"#00394d\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"accessibility\": \"wheelChair\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Brunnsparken, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014001760003\",\n" +
            "              \"routeIdx\": \"6\",\n" +
            "              \"time\": \"09:01\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"C\",\n" +
            "              \"rtTime\": \"09:07\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"routeIdx\": \"7\",\n" +
            "              \"time\": \"09:04\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"rtTime\": \"09:10\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=266325%2F94139%2F96030%2F40761%2F80%3Fdate%3D2019-09-24%26station_evaId%3D1760003%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Gå\",\n" +
            "            \"type\": \"WALK\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242002\",\n" +
            "              \"time\": \"09:09\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"B\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"time\": \"09:09\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"286 Älvsnabbare\",\n" +
            "            \"sname\": \"286\",\n" +
            "            \"journeyNumber\": \"24\",\n" +
            "            \"type\": \"BOAT\",\n" +
            "            \"id\": \"9015014528600024\",\n" +
            "            \"reachable\": \"false\",\n" +
            "            \"direction\": \"Lindholmspiren, Fri resa\",\n" +
            "            \"fgColor\": \"#00A5DC\",\n" +
            "            \"bgColor\": \"#ffffff\",\n" +
            "            \"stroke\": \"Solid\",\n" +
            "            \"Origin\": {\n" +
            "              \"name\": \"Stenpiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014006242005\",\n" +
            "              \"routeIdx\": \"0\",\n" +
            "              \"time\": \"09:10\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"E\",\n" +
            "              \"rtTime\": \"09:10\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Destination\": {\n" +
            "              \"name\": \"Lindholmspiren, Göteborg\",\n" +
            "              \"type\": \"ST\",\n" +
            "              \"id\": \"9022014004493001\",\n" +
            "              \"routeIdx\": \"1\",\n" +
            "              \"time\": \"09:16\",\n" +
            "              \"date\": \"2019-09-24\",\n" +
            "              \"track\": \"A\",\n" +
            "              \"rtTime\": \"09:16\",\n" +
            "              \"rtDate\": \"2019-09-24\",\n" +
            "              \"$\": \"\\n\"\n" +
            "            },\n" +
            "            \"Notes\": {\n" +
            "              \"Note\": {\n" +
            "                \"key\": \"other\",\n" +
            "                \"severity\": \"low\",\n" +
            "                \"priority\": \"1\",\n" +
            "                \"$\": \"Färjan är avgiftsfri.\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"JourneyDetailRef\": {\n" +
            "              \"ref\": \"https://api.vasttrafik.se/bin/rest.exe/v2/journeyDetail?ref=403233%2F145389%2F1622%2F133621%2F80%3Fdate%3D2019-09-24%26station_evaId%3D6242005%26station_type%3Ddep%26format%3Djson%26\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

}
