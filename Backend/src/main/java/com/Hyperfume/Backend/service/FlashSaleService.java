package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.FlashSaleRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.entity.FlashSale;

public interface FlashSaleService {
    FlashSaleResponse getActiveFlashSale();

    PageResponse<FlashSaleResponse> getUpcomingFlashSales(int page, int size);

    PageResponse<FlashSaleResponse> getPastFlashSales(int page, int size);

    //    PageResponse<FlashSaleResponse> getInactiveCurrentFlashSales(int page, int size);

    FlashSaleResponse getFlashSaleById(int flashSaleId);

    FlashSaleResponse createFlashSale(FlashSaleRequest request);

    FlashSaleResponse updateFlashSale(int flashSaleId, FlashSaleRequest request);

    FlashSaleResponse toggleFlashSaleStatus(int flashSaleId, boolean active);

    FlashSaleResponse addPerfumeToFlashSale(int flashSaleId, int perfumeId, int quantityLimit, Double specialDiscount);

    FlashSaleResponse removePerfumeFromFlashSale(int flashSaleId, int flashSaleItemId);

    void scheduledFlashSale();

    boolean incrementFlashSaleItemSold(int flashSaleItemId, int quantity);

    void activateFlashSale(FlashSale flashSale);

    void deactivateFlashSale(FlashSale flashSale);

    //    boolean updateSoldQuantity(int flashSaleItemId, int quantitySold);

    //    boolean incrementSoldQuantity(int flashSaleItemId);
}
