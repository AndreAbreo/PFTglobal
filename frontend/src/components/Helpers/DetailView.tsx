"use client";
import React from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

export interface Column<T> {
  
  header: string;
  
  accessor: keyof T | ((row: T) => React.ReactNode);
  
  type?: "text" | "date" | "image" | "number" | "phone" | "email";
  
  filterable?: boolean;
}

interface DetailViewProps<T> {
  data: T;
  columns: Column<T>[];
  
  backLink?: string;
  
  showEditButton?: boolean;
}

const DetailView = <T extends {}>({ data, columns, backLink, showEditButton = false }: DetailViewProps<T>) => {
  const router = useRouter();

  const handleEdit = () => {

    const currentPath = window.location.pathname;

    const editPath = currentPath.replace('/ver/', '/editar/');

    router.push(editPath);
  };

  return (
    <div className="p-6 bg-white rounded-lg shadow-md">
      {columns.map((col, index) => {
        let value: React.ReactNode;
        
        try {
          if (typeof col.accessor === "function") {
            value = col.accessor(data);
          } else {
            value = (data as any)[col.accessor] as React.ReactNode;
          }

          if (col.type === "date" && typeof value === "string") {
            value = new Date(value).toLocaleDateString();
          }

          if (col.type === "image" && typeof value === "string") {
            value = <img src={value} alt={col.header} className="max-w-xs rounded" />;
          }

          return (
            <div key={index} className="mb-4">
              <div className="font-bold text-gray-700">{col.header}:</div>
              <div className="text-gray-900">{value || "No disponible"}</div>
            </div>
          );
        } catch (error) {
          return (
            <div key={index} className="mb-4">
              <div className="font-bold text-gray-700">{col.header}:</div>
              <div className="text-gray-900 text-red-500">Error al cargar datos</div>
            </div>
          );
        }
      })}
      <div className="mt-6 flex gap-4">
        {backLink && (
          <Link
            href={backLink}
            className="inline-block px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Volver al listado
          </Link>
        )}
        {showEditButton && (
          <button
            onClick={handleEdit}
            className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
          >
            Editar
          </button>
        )}
      </div>
    </div>
  );
};

export default DetailView;
