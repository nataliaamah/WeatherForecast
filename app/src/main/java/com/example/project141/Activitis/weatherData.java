package com.example.project141.Activitis;


import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    private String imageView, location, description, temperature, tempHL, amountRain, windSpeed, humidity;
    private int condition;

    public static weatherData fromJson(JSONObject jsonObject) {

        try {
            weatherData weatherD = new weatherData();

            weatherD.location = jsonObject.getString("name");

            weatherD.description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            weatherD.imageView = updateWeatherIcon(weatherD.condition);

            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);
            weatherD.temperature = Integer.toString(roundedValue);

            double high = jsonObject.getJSONObject("main").getDouble("temp_max");
            double low = jsonObject.getJSONObject("main").getDouble("temp_min");
            weatherD.tempHL = "H:" + high + " L:" + low;

            weatherD.amountRain = Double.toString(jsonObject.getJSONObject("rain").getDouble("1h"));

            weatherD.windSpeed = Double.toString(jsonObject.getJSONObject("wind").getDouble("speed"));

            return weatherD;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstrom1";
        }
        else if(condition>=300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow2";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom1";
        }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom2";
        }

        return "dunno";


    }
    public String getmTemperature() {
        return temperature+"°C";
    }

    public String getMicon() {
        return imageView;
    }

    public String getMcity() {
        return location;
    }

    public String getmWeatherType() {
        return description;
    }

    public String getTempHL() {
        return tempHL;
    }

    public String getAmountRain() {
        return amountRain;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }
}