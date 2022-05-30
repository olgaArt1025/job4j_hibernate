package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private static BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_004.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        pool.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = pool.getConnection().prepareStatement("delete from orders")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindNameAndFindId() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        Order order2 = Order.of("name2", "description2");
        store.save(order2);
        Order order3 = Order.of("name3", "description3");
        store.save(order3);

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(3));
        assertThat(store.findByName("name2"), is(order2));
        assertThat(store.findById(3), is(order3));
    }

    @Test
    public void whenSaveOrderAndUpdateAndFindId() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        Order order2 = Order.of("name2", "description2");
        store.save(order2);

        List<Order> all = (List<Order>) store.findAll();
        store.update(order2.getId(), Order.of("newName", "description3"));

        assertThat(all.size(), is(2));
        assertThat(store.findByName("name1"), is(order));
        Assert.assertEquals(store.findById(order2.getId()).getName(), "newName");
    }
}