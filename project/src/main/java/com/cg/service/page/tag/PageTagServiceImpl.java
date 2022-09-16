package com.cg.service.page.tag;

import com.cg.model.dto.TagDTO;
import com.cg.repository.page.PaginAndSortTingTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PageTagServiceImpl implements PageTagService {
    @Autowired
    private PaginAndSortTingTagRepository paginAndSortTingTagRepository;

    @Override
    public Page<TagDTO> findAllProductDTONoImage(Pageable pageable) {
        return paginAndSortTingTagRepository.findAllProductDTONoImage(pageable);
    }

    public Page<TagDTO> findALl(String Categories, String Color, int choice, int option, String title, Pageable pageable) {
        if (Color.equals("%null%")) {
            if (Categories.equals("%null%")) {
                if (option == 1) {
                    if (choice == 1) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, Color, pageable);
                    }
                    if (choice == 2) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, Color, pageable);
                    }
                    if (choice == 3) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, Color, pageable);
                    }
                    if (choice == 4) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
                    }
                    if (choice == 5) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
                    }
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
                }
            }

            if (option == 1) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, pageable);
            }
        }

        if (Categories.equals("%null%")) {
            if (option == 1) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Color, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Color, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Color, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Color, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Color, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Color, pageable);
            }
        }

        if (option == 1) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, Color, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, Color, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, Color, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
        }


        if (Color.equals("%null%")) {
            if (Categories.equals("%null%")) {
                if (option == 2) {
                    if (choice == 1) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
                    }
                    if (choice == 2) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
                    }
                    if (choice == 3) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
                    }
                    if (choice == 4) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
                    }
                    if (choice == 5) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
                    }
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
                }
            }

            if (option == 2) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Categories, pageable);
            }
        }

        if (Categories.equals("%null%")) {
            if (option == 2) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Color, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Color, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Color, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Color, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Color, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Color, pageable);
            }
        }

        if (option == 2) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, Color, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, Color, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, Color, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
        }


        if (Color.equals("%null%")) {
            if (Categories.equals("%null%")) {
                if (option == 3) {
                    if (choice == 1) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
                    }
                    if (choice == 2) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
                    }
                    if (choice == 3) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
                    }
                    if (choice == 4) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
                    }
                    if (choice == 5) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
                    }
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
                }
            }

            if (option == 3) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Categories, pageable);
            }
        }

        if (Categories.equals("%null%")) {
            if (option == 3) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Color, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Color, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Color, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Color, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Color, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Color, pageable);
            }
        }

        if (option == 3) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, Color, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, Color, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, Color, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
        }


        if (Color.equals("%null%")) {
            if (Categories.equals("%null%")) {
                if (option == 4) {
                    if (choice == 1) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
                    }
                    if (choice == 2) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
                    }
                    if (choice == 3) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
                    }
                    if (choice == 4) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
                    }
                    if (choice == 5) {
                        return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
                    }
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, pageable);
                }
            }

            if (option == 4) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, pageable);
            }
        }

        if (Categories.equals("%null%")) {
            if (option == 4) {
                if (choice == 1) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Color, pageable);
                }
                if (choice == 2) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Color, pageable);
                }
                if (choice == 3) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Color, pageable);
                }
                if (choice == 4) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Color, pageable);
                }
                if (choice == 5) {
                    return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Color, pageable);
                }
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Color, pageable);
            }
        }

        if (option == 4) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, Color, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, Color, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories, Color, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, Color, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, Color, pageable);
        }


        return null;
    }
}
