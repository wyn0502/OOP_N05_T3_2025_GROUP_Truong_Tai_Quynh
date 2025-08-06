package com.example.zoo.interfaces;

import java.util.List;

/**
 * @author TRUONG
  */

  public interface IManager<T> {
      void them(T obj);

          List<T> hienThi();

              void sua(String id, T obj);

                  void xoa(String id);
                  }
                  