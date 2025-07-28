"use client";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import PanelMetricas from "@/components/Paginas/PanelMetricas";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Panel de Métricas",
};

export default function ApiPage() {
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Panel de Métricas" />
      <div className="flex flex-col gap-10">
        <PanelMetricas />
      </div>
    </DefaultLayout>
  );
}