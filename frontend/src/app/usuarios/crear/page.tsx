import RegisterForm from "@/components/Helpers/RegisterForm";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { Metadata } from "next";

export const metadata: Metadata = {
    title: "Creación de usuario",
};

const CrearUsuarioPage = () => {
    return (
        <DefaultLayout>
            <Breadcrumb pageName="Crear Usuario" />
            <RegisterForm requiresAuth={true} />
        </DefaultLayout>
    );
};

export default CrearUsuarioPage;