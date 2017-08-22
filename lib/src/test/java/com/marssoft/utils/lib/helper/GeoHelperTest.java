package com.marssoft.utils.lib.helper;

import com.geo.GeocodeResponse;
import com.google.gson.Gson;
import junit.framework.TestCase;

import com.marssoft.utils.lib.json.MyGsonBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Alexey on 22.07.2015.
 */
public class GeoHelperTest extends TestCase  {

    @Test
    public void testGeocoding() throws IOException, InterruptedException {
    }

    @Test
    public void testReverseGeocoding() throws IOException {
    }

    @Test
    public void testParsingJson() throws IOException {
        String json = "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"50\",\n" +
                "               \"short_name\" : \"50\",\n" +
                "               \"types\" : [ \"street_number\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"вулиця Шовковична\",\n" +
                "               \"short_name\" : \"вулиця Шовковична\",\n" +
                "               \"types\" : [ \"route\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Печерський район\",\n" +
                "               \"short_name\" : \"Печерський район\",\n" +
                "               \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"вулиця Шовковична, 50, Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.4395652,\n" +
                "               \"lng\" : 30.5257681\n" +
                "            },\n" +
                "            \"location_type\" : \"ROOFTOP\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.4409141802915,\n" +
                "                  \"lng\" : 30.5271170802915\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.4382162197085,\n" +
                "                  \"lng\" : 30.5244191197085\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJH0QlJwDP1EARqkJwBjK5UQQ\",\n" +
                "         \"types\" : [ \"street_address\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Липки\",\n" +
                "               \"short_name\" : \"Липки\",\n" +
                "               \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Печерський район\",\n" +
                "               \"short_name\" : \"Печерський район\",\n" +
                "               \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Липки, Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.45225199999999,\n" +
                "                  \"lng\" : 30.539782\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.43704899999999,\n" +
                "                  \"lng\" : 30.5205273\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.444444,\n" +
                "               \"lng\" : 30.534444\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.45225199999999,\n" +
                "                  \"lng\" : 30.539782\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.43704899999999,\n" +
                "                  \"lng\" : 30.5205273\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJWf4Ez1TO1EARBuAQDDAIthw\",\n" +
                "         \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Печерський район\",\n" +
                "               \"short_name\" : \"Печерський район\",\n" +
                "               \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Печерський район, Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.4569339,\n" +
                "                  \"lng\" : 30.588075\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.402478,\n" +
                "                  \"lng\" : 30.5160829\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.420556,\n" +
                "               \"lng\" : 30.549444\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.4569339,\n" +
                "                  \"lng\" : 30.588075\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.402478,\n" +
                "                  \"lng\" : 30.5160829\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJzT4aNwvP1EARbOJiDoQqlHs\",\n" +
                "         \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.4501,\n" +
                "               \"lng\" : 30.5234\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJBUVa4U7P1EAR_kYBF9IxSXY\",\n" +
                "         \"types\" : [ \"locality\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"02000\",\n" +
                "               \"short_name\" : \"02000\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Київ, Україна, 02000\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.4501,\n" +
                "               \"lng\" : 30.5234\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJBUVa4U7P1EARxX1LbAvqE6A\",\n" +
                "         \"types\" : [ \"postal_code\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"місто Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.4501,\n" +
                "               \"lng\" : 30.5234\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJBUVa4U7P1EARBBiQlxMdJV8\",\n" +
                "         \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"місто Київ, місто Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.4501,\n" +
                "               \"lng\" : 30.5234\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.590798,\n" +
                "                  \"lng\" : 30.825941\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.213273,\n" +
                "                  \"lng\" : 30.2394401\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJBUVa4U7P1EARcgG8M175HHk\",\n" +
                "         \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 52.37958099999999,\n" +
                "                  \"lng\" : 40.228581\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 44.386463,\n" +
                "                  \"lng\" : 22.1371589\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 48.379433,\n" +
                "               \"lng\" : 31.16558\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 52.37958099999999,\n" +
                "                  \"lng\" : 40.228581\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 44.386463,\n" +
                "                  \"lng\" : 22.1371589\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJjw5wVMHZ0UAREED2iIQGAQA\",\n" +
                "         \"types\" : [ \"country\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"вул. Басейна\",\n" +
                "               \"short_name\" : \"вул. Басейна\",\n" +
                "               \"types\" : [ \"point_of_interest\", \"establishment\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Печерський район\",\n" +
                "               \"short_name\" : \"Печерський район\",\n" +
                "               \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Київ\",\n" +
                "               \"short_name\" : \"Київ\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"місто Київ\",\n" +
                "               \"short_name\" : \"місто Київ\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Україна\",\n" +
                "               \"short_name\" : \"UA\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"вул. Басейна, Київ, Україна\",\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 50.438419,\n" +
                "               \"lng\" : 30.526392\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 50.43976798029149,\n" +
                "                  \"lng\" : 30.5277409802915\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 50.43707001970849,\n" +
                "                  \"lng\" : 30.5250430197085\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJwYE9NQDP1EARjVqV3i4h1Uc\",\n" +
                "         \"types\" : [ \"transit_station\", \"point_of_interest\", \"establishment\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}\n";
        Gson gson = MyGsonBuilder.getInstance();

        GeocodeResponse geocodeResponse = gson.fromJson(json, GeocodeResponse.class);

    }

}
