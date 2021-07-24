package com.sweng.recipebook.data;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sweng.recipebook.models.RecipeReview;
import com.sweng.recipebook.models.ReviewComposite;

/**
 * ReviewDataAccess - Data access class for application recipe reviews.
 */
public class ReviewDataAccess extends DataAccess {

    public ReviewDataAccess() {
        super();
    }

    /**
     * deleteReview - Method to remove a review from the reviews table.
     * 
     * @param userId   - User id number.
     * @param recipeId - Recipe id number.
     * @throws SQLException
     */
    public void deleteReview(int userId, int recipeId) throws SQLException {
        String dml = "DELETE FROM recipebook_recipe_reviews WHERE user_id = ? AND recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        try {
            statement.setInt(1, userId);
            statement.setInt(2, recipeId);
            statement.execute();
        } finally {
            statement.close();
        }
    }

    /**
     * getReviews - Method to retrieve a ReviewComposite of recipe reviews.
     * 
     * @param recipeId - Recipe id number.
     * @return - ReviewComposite.
     * @throws SQLException
     */
    public ReviewComposite getReviews(int recipeId) throws SQLException {
        ReviewComposite result = new ReviewComposite();
        String query = "SELECT first_name || ' ' || SUBSTR(last_name, 1, 1) || '.' AS critic, rating, comments FROM recipebook_recipe_reviews JOIN recipebook_user USING (user_id) WHERE recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.addReview(new RecipeReview(resultSet.getString("critic"), resultSet.getInt("rating"),
                        new String(resultSet.getBytes("comments"))));
            }
        } finally {
            statement.close();
        }
        return result;
    }

    /**
     * hasReviewed - Method to determine if user has reviewed the recipe.
     * 
     * @param recipeId - Recipe id number.
     * @param userId   - User id number.
     * @return True if reviewed, otherwise false.
     * @throws SQLException
     */
    public boolean hasReviewed(int recipeId, int userId) throws SQLException {
        String query = "SELECT recipe_id, user_id FROM recipebook_recipe_reviews WHERE recipe_id = ? AND user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.isBeforeFirst();
        } finally {
            statement.close();
        }
    }

    /**
     * insertReview - Method to insert a user review for a recipe.
     * 
     * @param userId   - User id number.
     * @param recipeId - Recipe id number.
     * @param rating   - Recipe user rating.
     * @param comments - User comments for recipe.
     * @throws SQLException
     */
    public void insertReview(int userId, int recipeId, int rating, String comments) throws SQLException {
        String dml = "INSERT INTO recipebook_recipe_reviews (user_id, recipe_id, rating, comments) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        Blob blob = connection.createBlob();
        try {
            blob.setBytes(1, this.correctJSONCharacters(comments).getBytes());
            statement.setInt(1, userId);
            statement.setInt(2, recipeId);
            statement.setInt(3, rating);
            statement.setBlob(4, blob);
            statement.execute();
        } finally {
            statement.close();
        }
    }
}
