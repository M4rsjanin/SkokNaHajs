import { Routes, Route, Link } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import { Button } from '@/components/ui/button'
import './index.css'
import DashboardPage from './pages/DashboardPage'
import ProtectedRoute from './components/ProtectedRoute'

function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-secondary via-background to-secondary">
      <header className="container mx-auto px-6 py-6 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="w-10 h-10 rounded-lg bg-primary flex items-center justify-center text-primary-foreground font-bold text-xl">
            S
          </div>
          <span className="text-2xl font-bold">Skok na Hajs</span>
        </div>
        <div className="flex gap-3">
          <Link to="/login">
            <Button variant="ghost">Zaloguj się</Button>
          </Link>
          <Link to="/register">
            <Button>Załóż konto</Button>
          </Link>
        </div>
      </header>

      <main className="container mx-auto px-6 py-20 flex flex-col items-center text-center">
        <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-accent/30 text-accent-foreground text-sm font-medium mb-6">
          <span className="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
          Bank nowej generacji
        </div>

        <h1 className="text-5xl md:text-7xl font-bold mb-6 max-w-3xl">
          Twoje pieniądze, <span className="text-primary">Twoje zasady</span>
        </h1>

        <p className="text-lg text-muted-foreground max-w-xl mb-10">
          Załóż konto w 2 minuty. Bez ukrytych opłat, bez papierologii, bez stresu.
          Bankowość prosta jak nigdy wcześniej.
        </p>

        <div className="flex flex-col sm:flex-row gap-4">
          <Link to="/register">
            <Button size="lg" className="text-base px-8">
              Załóż konto za darmo
            </Button>
          </Link>
          <Link to="/login">
            <Button size="lg" variant="outline" className="text-base px-8">
              Mam już konto
            </Button>
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-20 max-w-4xl w-full">
          <FeatureCard
            icon="⚡"
            title="Błyskawiczne przelewy"
            description="Pieniądze docierają w sekundę, 24/7."
          />
          <FeatureCard
            icon="🔒"
            title="Maksimum bezpieczeństwa"
            description="Szyfrowanie bankowe i 2FA na każdym kroku."
          />
          <FeatureCard
            icon="💰"
            title="Lokaty i kredyty"
            description="Najlepsze oprocentowanie na rynku."
          />
        </div>
      </main>
    </div>
  )
}

function FeatureCard({ icon, title, description }: { icon: string; title: string; description: string }) {
  return (
    <div className="bg-card border rounded-xl p-6 text-left hover:shadow-lg transition-shadow">
      <div className="text-3xl mb-3">{icon}</div>
      <h3 className="font-semibold text-lg mb-2">{title}</h3>
      <p className="text-muted-foreground text-sm">{description}</p>
    </div>
  )
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
  />
    </Routes>
  )
}

export default App