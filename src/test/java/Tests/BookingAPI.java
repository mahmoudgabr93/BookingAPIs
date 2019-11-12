package Tests;


import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;

import com.jayway.restassured.response.Response;
import org.testng.asserts.Assertion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertTrue;

public class BookingAPI {
    Response responseOfBookingsApi ;
    Response ResponseOfBookingApi ;
    int sizeOfList ;

    /*
    The getBookings method is the first test case for testing getBookings GET API
     */
    @Test (priority = 1)
    public void getBookings() {
         responseOfBookingsApi = given()
                .when()
                .get("https://automationintesting.online/booking/")
                .then()
                .extract()
                .response();
         sizeOfList = responseOfBookingsApi.body().path("bookings.size()");

      assertTrue(sizeOfList >= 2) ;
    }

    /*
    The getBooking method is the second test case for testing getBooking GET API
     */
    @Test (priority = 2)
    public void getBooking() {
        for (int i =0 ; i < sizeOfList ; i++)
        {
            ResponseOfBookingApi = given()
                    .when()
                    .get("https://automationintesting.online/booking/" + responseOfBookingsApi.body().path("bookings["+ i +"].bookingid"))
                    .then()
                    .extract()
                    .response();

            Assert.assertEquals(ResponseOfBookingApi.body().path("bookingid"), responseOfBookingsApi.body().path("bookings[" + i +"].bookingid"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("roomid"), responseOfBookingsApi.body().path("bookings[" + i +"].roomid"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("firstname"), responseOfBookingsApi.body().path("bookings[" + i +"].firstname"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("lastname"), responseOfBookingsApi.body().path("bookings[" + i +"].lastname"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("depositpaid"), responseOfBookingsApi.body().path("bookings[" + i +"].depositpaid"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("bookingdates.checkout"), responseOfBookingsApi.body().path("bookings[" + i +"].bookingdates.checkout"));
            Assert.assertEquals(ResponseOfBookingApi.body().path("bookingdates.checkin"), responseOfBookingsApi.body().path("bookings[" + i +"].bookingdates.checkin"));
        }



    }

    /*
    The createBooking method is the third test case for testing createBooking POST API
     */
    @Test (priority = 3)
    public void createBooking()
    {
    Date date = new Date();
    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-M-dd");
    String stringDate= DateFor.format(date);
    System.out.println(stringDate);

    Calendar now = Calendar.getInstance();

    int CurrentYear = now.get(Calendar.YEAR);
    // month start from 0 to 11
    int CurrentMonth =(now.get(Calendar.MONTH) + 1);

    if (CurrentMonth ==12)
    {
        CurrentYear++;
    }
    System.out.println( ++sizeOfList );
          String myJson = "{\n" +
                  "  \"bookingdates\": {\n" +
                  "    \"checkin\": \" " + stringDate + "\",\n" +
                  "    \"checkout\": \"" + CurrentYear + "-"+ "12"  +"-10\"\n" +
                  "  },\n" +
                  "  \"bookingid\": 10,\n" +
                  "  \"depositpaid\": true,\n" +
                  "  \"email\": \"mahmoudgabr.932@gmail.com\",\n" +
                  "  \"firstname\": \"mahmoud\",\n" +
                  "  \"lastname\": \"gabr\",\n" +
                  "  \"phone\": \"01023800402\",\n" +
                  "  \"roomid\": " + ++sizeOfList  + "\n" +
                  "}";


                      given()
                     .header("Content-Type", "application/json")
                     .body(myJson)
                     .when()
                     .post("https://automationintesting.online/booking/")
                     .then().statusCode(201);


    }
}