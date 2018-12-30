package com.item.microservices.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.item.microservices.domain.Item;

import java.sql.Date;

import java.util.List;

@Repository
public class ItemRepository {

	private final JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "insert into item_541455(id,name,description,price,created,modified) values(?,?,?,?,?,?)";
    private final String SQL_QUERY_ALL = "select * from item_541455";
    private final String SQL_QUERY_BY_ID = "select * from item_541455 where id=?";
    private final String SQL_QUERY_BY_NAME = "select * from item_541455 where name=?";

    private final RowMapper<Item> rowMapper = (ResultSet rs, int row) -> {
        Item item = new Item();
        item.setId(rs.getString("id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getString("price"));
        item.setCreated(rs.getDate("created"));
        item.setModified(rs.getDate("modified"));
        return item;
    };

    @Autowired
    public ItemRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Item save(Item item) {
        assert item.getName() != null;
        assert item.getDescription() != null;
        assert item.getPrice() != null;

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
            ps.setString(1, item.getId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getPrice());
            ps.setDate(5, new Date(item.getCreated().getTime()));
            ps.setDate(6, new Date(item.getModified().getTime()));
            return ps;
        });

        return item;
    }

    public  List<Item> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_ALL, rowMapper);
    }

    public Item findOne(String id) {
        return this.jdbcTemplate.queryForObject(SQL_QUERY_BY_ID, new Object[]{id}, rowMapper);
    }
    
    public Item findByName(String name) {
        return this.jdbcTemplate.queryForObject(SQL_QUERY_BY_NAME, new Object[]{name}, rowMapper);
    }
}
