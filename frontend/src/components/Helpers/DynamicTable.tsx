"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import * as XLSX from "xlsx";


export interface Column<T> {
  
  header: string;
  
  accessor: keyof T | ((row: T) => React.ReactNode);
  
  type?: "text" | "date" | "image" | "number" | "phone" | "dropdown" | "email";
  
  filterable?: boolean;
  
  options?: Array<{ value: string; label: string }>;
  
  filterKey?: string;
  
  imageConfig?: {
    
    width?: number;
    
    height?: number;
    
    className?: string;
    
    objectFit?: "contain" | "cover" | "fill" | "none" | "scale-down";
  };
}


interface DynamicTableProps<T extends { id: number }> {
  
  readonly columns: ReadonlyArray<Column<T>>;
  
  readonly data: ReadonlyArray<T>;
  
  readonly withFilters?: boolean;
  
  readonly onSearch?: (filters: Record<string, string>) => void;
  
  readonly filterUrl?: string;
  
  readonly initialFilters?: Record<string, string>;
  
  readonly withActions?: boolean;
  
  readonly deleteUrl?: string;
  
  readonly basePath?: string;
  
  readonly onDelete?: (id: number, row: T) => Promise<{ message: string }>;
  
  readonly selectUrl?: string;
  
  readonly sendOnlyId?: boolean;
  
  readonly onDataUpdate?: (data: T[]) => void;
  
  readonly confirmDeleteMessage?: string;
  
  readonly onReload?: () => Promise<void>;
  
  readonly includeSinValidar?: boolean;
}


function DynamicTable<T extends { id: number }>({
  columns,
  data,
  withFilters = false,
  onSearch,
  filterUrl,
  initialFilters = {},
  withActions = false,
  deleteUrl,
  basePath = "",
  onDelete,
  selectUrl,
  sendOnlyId = false,
  onDataUpdate,
  confirmDeleteMessage,
  onReload,
  includeSinValidar = false,
}: DynamicTableProps<T>) {


  
  const [deletionError, setDeletionError] = useState<string>("");
  const [showDeletionErrorModal, setShowDeletionErrorModal] = useState(false);
  
  const [showConfirmDeleteModal, setShowConfirmDeleteModal] = useState(false);
  const [rowIdToDelete, setRowIdToDelete] = useState<number | null>(null);
  
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [successMessage, setSuccessMessage] = useState<string>("");

  
  const [filters, setFilters] = useState<Record<string, string>>(initialFilters);

  
  
  useEffect(() => {
    if (filterUrl) {
      const loadInitialData = async () => {
        try {

          let url = filterUrl;
          if (Object.keys(initialFilters).length > 0) {
            const params = new URLSearchParams();
            Object.entries(initialFilters).forEach(([key, value]) => {
              if (value) params.append(key, value);
            });
            const queryString = params.toString() ? `?${params.toString()}` : "";
            url = `${filterUrl}${queryString}`;
          }
          
          const initialData = await fetcher<T[]>(url, { method: "GET" });
          if (onDataUpdate) {
            onDataUpdate(initialData);
          }
        } catch (error: any) {
          console.error("Error al cargar datos iniciales:", error);
        }
      };
      loadInitialData();
    }
  }, [filterUrl]); // Solo se ejecuta cuando cambia filterUrl

  
  
  const handleFilterChange = (key: string, value: string) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  
  const handleSearch = async () => {
    if (filterUrl) {

      try {
        const params = new URLSearchParams();
        Object.entries(filters).forEach(([key, value]) => {
          if (value) params.append(key, value);
        });
        const queryString = params.toString() ? `?${params.toString()}` : "";
        const filteredData = await fetcher<T[]>(`${filterUrl}${queryString}`, { method: "GET" });
        if (onDataUpdate) {
          onDataUpdate(filteredData);
        }
      } catch (error: any) {
        console.error("Error al filtrar:", error);
      }
    } else if (onSearch) {

      onSearch(filters);
    }
  };

  
  const reloadData = async () => {

    await new Promise(resolve => setTimeout(resolve, 100));
    
    if (filterUrl) {

      try {
        const params = new URLSearchParams();
        Object.entries(filters).forEach(([key, value]) => {
          if (value) params.append(key, value);
        });
        const queryString = params.toString() ? `?${params.toString()}` : "";
        const filteredData = await fetcher<T[]>(`${filterUrl}${queryString}`, { method: "GET" });
        if (onDataUpdate) {
          onDataUpdate(filteredData);
        }
      } catch (error: any) {
        console.error("Error al recargar datos:", error);

      }
    } else if (onSearch) {

      try {
        onSearch(filters);
      } catch (error: any) {
        console.error("Error al recargar datos con onSearch:", error);
      }
    }
  };

  
  const handleClearFilters = async () => {
    const cleared: Record<string, string> = {};
    columns.forEach((col, idx) => {
      if (col.filterable) {
        const key = col.filterKey || (typeof col.accessor === "string" ? col.accessor : `filter_${idx}`);
        cleared[key] = "";
      }
    });
    setFilters(cleared);
    
    if (filterUrl) {

      try {
        const filteredData = await fetcher<T[]>(filterUrl, { method: "GET" });
        if (onDataUpdate) {
          onDataUpdate(filteredData);
        }
      } catch (error: any) {
        console.error("Error al limpiar filtros:", error);
      }
    } else if (onSearch) {

      onSearch(cleared);
    }
  };


  
  const prepareDeleteRequest = async (id: number): Promise<{ url: string; body?: any }> => {
    let body = undefined;
    let url = deleteUrl!;

    if (sendOnlyId) {
      if (selectUrl) {
        body = { id };
      } else {
        url = `${deleteUrl}?id=${id}`;
      }
    } else if (selectUrl) {
      body = await fetcher<T>(`${selectUrl}?id=${id}`, { method: "GET" });
    } else {

      const row = data.find((item) => item.id === id);
      if (row) {
        body = row;
      } else {

        try {
          const selectUrl = deleteUrl!.replace('/inactivar', '/seleccionar');
          body = await fetcher<T>(`${selectUrl}?id=${id}`, { method: "GET" });
        } catch (error) {
          console.error("Error al obtener objeto para eliminación:", error);
          throw new Error("No se pudo obtener el objeto para eliminar");
        }
      }
    }

    return { url, body };
  };

  
  const handleDeleteResponse = (response: { message?: string; error?: string }) => {
    if (response.message) {
      setSuccessMessage(response.message);
      setShowSuccessModal(true);
      if (onReload) {
        onReload();
      } else {
        reloadData();
      }
    } else {
      setDeletionError(response.error || "Error al eliminar.");
      setShowDeletionErrorModal(true);
    }
  };

  
  const handleDeleteError = (error: any) => {
    const errorMsg =
      error?.response?.error || error?.response?.message || error.message || "Error al eliminar.";
    setDeletionError(errorMsg);
    setShowDeletionErrorModal(true);
  };

  
  const handleDelete = async (id: number) => {
    const row = data.find((item) => item.id === id);
    if (!row) return;
    
    try {
      let response: { message?: string; error?: string };

      if (onDelete) {
        response = await onDelete(id, row);
      } else if (deleteUrl) {
        const { url, body } = await prepareDeleteRequest(id);
        response = await fetcher<{ message?: string; error?: string }>(url, {
          method: "PUT",
          ...(body ? { body } : {}),
        });
      } else {
        throw new Error("No se definió una lógica de eliminación.");
      }

      await handleDeleteResponse(response);
    } catch (error: any) {
      handleDeleteError(error);
    }
  };

  
  
  const formatDate = (dateString: string) => {
    if (!dateString) return "";
    const date = new Date(dateString);

    date.setMinutes(date.getMinutes() + date.getTimezoneOffset());
    return date.toLocaleDateString();
  };

  
  const handleExportExcel = () => {

    const exportData = data.map((row) => {
      const rowData: Record<string, any> = {};
      columns.forEach((col) => {
        let value;
        
        if (typeof col.accessor === "function") {

          const rendered = col.accessor(row);
          if (
            typeof rendered === "string" ||
            typeof rendered === "number" ||
            typeof rendered === "boolean"
          ) {
            value = rendered;
          } else if (React.isValidElement(rendered)) {

            const children = (rendered as React.ReactElement<any, any>).props?.children;
            if (
              typeof children === "string" ||
              typeof children === "number" ||
              typeof children === "boolean"
            ) {
              value = children;
            } else {
              value = "";
            }
          } else {
            value = "";
          }
        } else {

          value = row[col.accessor];
        }
        rowData[col.header] = value;
      });
      return rowData;
    });

    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Datos");
    XLSX.writeFile(workbook, "exportacion_tabla.xlsx");
  };

  
  
  const renderFilterField = (col: Column<T>, idx: number) => {
    if (!col.filterable) return null;

    const key = col.filterKey || (typeof col.accessor === "string" ? col.accessor : `filter_${idx}`);
    const currentValue = filters[key] || "";

    if (key === "estado") {
      return (
        <div key={idx} className="flex flex-col">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
            {col.header}:
          </label>
          <select
            value={currentValue}
            onChange={(e) => handleFilterChange(key, e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 dark:bg-boxdark dark:border-boxdark-2 dark:text-white"
          >
            <option value="">Todos</option>
            <option value="ACTIVO">✅Activos</option>
            {includeSinValidar && <option value="SIN_VALIDAR">⛔Sin validar</option>}
            <option value="INACTIVO">❌Eliminados</option>
          </select>
        </div>
      );
    }

    if (col.type === "dropdown" && col.options) {
      return (
        <div key={idx} className="flex flex-col">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
            {col.header}:
          </label>
          <select
            value={currentValue}
            onChange={(e) => handleFilterChange(key, e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 dark:bg-boxdark dark:border-boxdark-2 dark:text-white"
          >
            <option value="">Todos</option>
            {col.options.map((option, optionIdx) => (
              <option key={`${option.value}-${optionIdx}`} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>
      );
    }

    if (col.type === "date") {
      return (
        <div key={idx} className="flex flex-col">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
            {col.header}:
          </label>
          <input
            type="date"
            value={currentValue}
            onChange={(e) => handleFilterChange(key, e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 dark:bg-boxdark dark:border-boxdark-2 dark:text-white"
          />
        </div>
      );
    }

    return (
      <div key={idx} className="flex flex-col">
        <label className="text-sm font-medium text-gray-700 dark:text-gray-200">
          {col.header}:
        </label>
        <input
          type="text"
          value={currentValue}
          onChange={(e) => handleFilterChange(key, e.target.value)}
          className="mt-1 block w-full rounded border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 dark:bg-boxdark dark:border-boxdark-2 dark:text-white"
        />
      </div>
    );
  };

  
  
  const renderByType = (col: Column<T>, value: any): React.ReactNode => {
    switch (col.type) {
      case "date":
        return value ? formatDate(value as string) : "";
      case "image":
        return value ? (
          <img
            src={value as string}
            alt={col.header}
            className={`rounded ${col.imageConfig?.className || "max-w-xs"}`}
            style={{
              width: col.imageConfig?.width ? `${col.imageConfig.width}px` : undefined,
              height: col.imageConfig?.height ? `${col.imageConfig.height}px` : undefined,
              objectFit: col.imageConfig?.objectFit || "contain"
            }}
          />
        ) : "";
      case "phone":
        return Array.isArray(value)
          ? (value
              .map((tel) => tel.numero || tel)
              .join(", ")) as React.ReactNode
          : (value as React.ReactNode);
      default:
        return value as React.ReactNode;
    }
  };

  
  const renderEstado = (value: any): React.ReactNode => {
    let colorClass = "";
    let label: string = String(value);
    
    if (value === "ACTIVO") {
      colorClass = "bg-green-100 text-green-800";
      label = "Activo";
    } else if (value === "INACTIVO") {
      colorClass = "bg-red-100 text-red-800";
      label = "Inactivo";
    } else if (value === "SIN_VALIDAR") {
      colorClass = "bg-yellow-100 text-yellow-800";
      label = "Sin validar";
    }
    
    return (
      <span className={`px-2 py-1 rounded-full text-xs ${colorClass}`}>
        {label}
      </span>
    );
  };

  
  const renderCellContent = (col: Column<T>, row: T) => {
    if (typeof col.accessor === "function") {
      return col.accessor(row);
    }
    
    const value = row[col.accessor];

    if (typeof col.accessor === "string" && col.accessor === "estado") {
      return renderEstado(value);
    }
    
    return renderByType(col, value);
  };

  
  return (
    <div className="overflow-x-auto bg-white dark:bg-boxdark p-4 rounded shadow">
      {}
      {withFilters && (
        <div className="mb-4 p-4 border border-gray-200 rounded bg-gray-50 dark:bg-boxdark-2 dark:border-boxdark-2">
          <h3 className="text-lg font-semibold mb-2 text-gray-900 dark:text-white">Filtros</h3>
          <div className="flex flex-wrap gap-4">
            {columns.map((col, idx) => renderFilterField(col, idx))}
          </div>
          <div className="mt-4 flex gap-4">
            <button
              onClick={handleSearch}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              Buscar
            </button>
            <button
              onClick={handleClearFilters}
              className="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400"
            >
              Borrar Filtros
            </button>
          </div>
        </div>
      )}

      {}
      <div className="flex justify-end mb-2 gap-2">
        <button
          onClick={() => {
            window.location.href = window.location.href;
          }}
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 text-sm flex items-center gap-2"
          title="Refrescar datos"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
          </svg>
          Refrescar
        </button>
        <button
          onClick={handleExportExcel}
          className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 text-sm flex items-center gap-2"
          title="Exportar a Excel"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
          </svg>
          Exportar a Excel
        </button>
      </div>

      {}
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-100 dark:bg-boxdark-2">
          <tr>
            {columns.map((col, idx) => (
              <th
                key={`header-${col.header}-${idx}`}
                scope="col"
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                {col.header}
              </th>
            ))}
            {withActions && (
              <th
                scope="col"
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
              >
                Acciones
              </th>
            )}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200 dark:bg-boxdark dark:divide-boxdark-2">
          {data.map((row, rowIndex) => (
            <tr
              key={`row-${row.id}-${rowIndex}`}
              className={
                rowIndex % 2 === 0
                  ? "bg-gray-50 dark:bg-boxdark-2"
                  : "bg-white dark:bg-boxdark"
              }
            >
              {}
              {columns.map((col, colIndex) => (
                  <td key={`cell-${row.id}-${col.header}-${colIndex}`} className="px-6 py-4 whitespace-normal break-words text-sm text-gray-900 dark:text-gray-200">
                  {renderCellContent(col, row)}
                  </td>
              ))}
              
              {}
              {withActions && (
                 <td className="px-6 py-4 whitespace-normal break-words text-sm text-gray-900 dark:text-gray-200">
                  <div className="flex items-center space-x-2">
                    {}
                    <a
                      href={`${basePath}/ver/${row.id}`}
                      className="text-blue-600 hover:underline flex items-center gap-1"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                      </svg>
                      <span className="sr-only">Ver</span>
                    </a>
                    
                    {}
                    <a
                      href={`${basePath}/editar/${row.id}`}
                      className="text-green-600 hover:underline flex items-center gap-1"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536M9 13l6.586-6.586a2 2 0 112.828 2.828L11.828 15.828a2 2 0 01-2.828 0L9 13zm-6 6h12M8 7V5a2 2 0 012-2h4a2 2 0 012 2v2" />
                      </svg>
                      <span className="sr-only">Editar</span>
                    </a>
                    
                    {}
                    <button
                      onClick={() => {
                        setRowIdToDelete(row.id);
                        setShowConfirmDeleteModal(true);
                      }}
                      className="text-red-600 hover:underline flex items-center gap-1"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6M1 7h22M8 7V5a2 2 0 012-2h4a2 2 0 012 2v2" />
                      </svg>
                      <span className="sr-only">Eliminar</span>
                    </button>
                  </div>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>

      {}
      
      {}
      {showDeletionErrorModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded shadow-lg max-w-md w-full text-center">
            <h3 className="text-xl font-semibold text-red-600 mb-4">Error al eliminar</h3>
            <p className="text-gray-700 mb-6">{deletionError}</p>
            <button
              onClick={() => setShowDeletionErrorModal(false)}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              Cerrar
            </button>
          </div>
        </div>
      )}
      
      {}
      {showConfirmDeleteModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded shadow-lg max-w-md w-full text-center">
            <h3 className="text-xl font-semibold mb-4">Confirmar eliminación</h3>
            <p className="text-gray-700 mb-6">{confirmDeleteMessage || "¿Está seguro que desea eliminar este registro?"}</p>
            <div className="flex justify-end gap-4">
              <button
                onClick={() => setShowConfirmDeleteModal(false)}
                className="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400"
              >
                Volver
              </button>
              <button
                onClick={async () => {
                  if (rowIdToDelete !== null) {
                    setShowConfirmDeleteModal(false);
                    await handleDelete(rowIdToDelete);
                    setRowIdToDelete(null);
                  }
                }}
                className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
              >
                Eliminar
              </button>
            </div>
          </div>
        </div>
              )}
        
        {}
        {showSuccessModal && (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-6 rounded shadow-lg max-w-md w-full text-center">
              <h3 className="text-xl font-semibold text-green-600 mb-4">Eliminación exitosa</h3>
              <p className="text-gray-700 mb-6">{successMessage}</p>
              <button
                onClick={() => setShowSuccessModal(false)}
                className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
              >
                Cerrar
              </button>
            </div>
          </div>
        )}
      </div>
    );
  }

export default DynamicTable;
