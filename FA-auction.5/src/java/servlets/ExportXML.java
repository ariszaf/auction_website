/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.AuctionDAO;
import entities.Auction;
import entities.Bids;
import entities.Category;
import entities.User;
import entities.UsersHasAuctions;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author lumberjack
 */
public class ExportXML extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuctionDAO exporter = new AuctionDAO();
        Collection<Auction> auctions = exporter.getAuctions();

        AuctionWrapper auctionWrapper = new AuctionWrapper();
        for (Auction entity : auctions) {
            AuctionModel model = new AuctionModel();
            model.setId(entity.getItemId());
            model.setName(entity.getName());
            for (Category cEntity : entity.getCategoryCollection()) {
                model.getCategory().add(cEntity.getName());
            }

            model.setCurrently(entity.getCurrently());
            model.setFirstbid(entity.getFirstBid());
            model.setNumber_of_bids(entity.getNumberOfBids());

            for (UsersHasAuctions sEntity : entity.getUsersHasAuctionsCollection()) {  // Theoro oti yparxei enas Seller
                model.setLocation(sEntity.getUser().getAddress() + " " + sEntity.getUser().getCity() + " "
                        + sEntity.getUser().getZipCode() + " " + sEntity.getUser().getCountry());
            }
            //model.setLocation(entity.);
            model.setCountry(entity.getCountry());
            model.setStarted(entity.getStarted());
            model.setEnds(entity.getEnds());

            SellerModel seller = new SellerModel();
            for (UsersHasAuctions sEntity : entity.getUsersHasAuctionsCollection()) {  // Theoro oti yparxei enas Seller
                seller.setRating(sEntity.getUser().getSellRating());
                seller.setUsername(sEntity.getUser().getUsername());
            }
            model.setSeller(seller);
            model.setDesrciption(entity.getDescription());

            BidsWrapper bidsWrapper = new BidsWrapper();
            for (Bids bEntity : entity.getBidsCollection()) {
                BidsModel bModel = new BidsModel();
                BitterModel bitter = new BitterModel();
                bitter.setRating(bEntity.getUser().getBidRating());
                bitter.setUserID(bEntity.getUser().getUsername());
                bitter.setLocation(bEntity.getUser().getAddress() + " " + bEntity.getUser().getCity() + " "
                        + bEntity.getUser().getZipCode() + " " + bEntity.getUser().getCountry());
                bitter.setCountry(bEntity.getUser().getCountry());
                bModel.setBitter(bitter);
                bModel.setTime(bEntity.getBidsPK().getTime());
                bModel.setBit_amount(bEntity.getBidAmount());
                bidsWrapper.getItem().add(bModel);
            }
            model.setBids(bidsWrapper);
            auctionWrapper.getItem().add(model);
        }
        
        try (PrintWriter out = response.getWriter()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuctionWrapper.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=dataset.xml");

            jaxbMarshaller.marshal(auctionWrapper, out);
        } catch (JAXBException ex) {
            Logger.getLogger(ExportXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
