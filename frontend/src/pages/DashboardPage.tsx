import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { accountApi } from '@/api/accountApi';
import type { AccountResponse } from '@/types/account';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';

function DashboardPage() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('dashboard');
  const [account, setAccount] = useState<AccountResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const firstName = localStorage.getItem('firstName');
  const email = localStorage.getItem('email');

  useEffect(() => {
    const fetchAccount = async () => {
      try {
        const data = await accountApi.getMyAccount();
        setAccount(data);
      } catch (err) {
        setError('Nie udało się pobrać danych konta');
      } finally {
        setLoading(false);
      }
    };
    fetchAccount();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('firstName');
    navigate('/');
  };

  const formatBalance = (balance: number, currency: string) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: currency,
    }).format(balance);
  };

  const formatAccountNumber = (accountNumber: string) => {
    return accountNumber.replace(/(.{4})/g, '$1 ').trim();
  };

  const navItems = [
    { id: 'dashboard', label: 'Pulpit', icon: '🏠' },
    { id: 'accounts', label: 'Konta', icon: '💳' },
    { id: 'transfers', label: 'Przelewy', icon: '💸' },
    { id: 'cards', label: 'Karty', icon: '🪪' },
    { id: 'deposits', label: 'Lokaty', icon: '🏦' },
    { id: 'loans', label: 'Kredyty', icon: '📊' },
    { id: 'settings', label: 'Ustawienia', icon: '⚙️' },
  ];

  return (
    <div className="min-h-screen flex bg-secondary/30">
      <aside className="w-64 bg-sidebar border-r flex flex-col">
        <div className="px-6 py-5 border-b">
          <div className="flex items-center gap-2">
            <div className="w-9 h-9 rounded-lg bg-primary flex items-center justify-center text-primary-foreground font-bold">
              S
            </div>
            <span className="font-bold text-lg">Skok na Hajs</span>
          </div>
        </div>

        <nav className="flex-1 px-3 py-4 space-y-1">
          {navItems.map((item) => (
            <button
              key={item.id}
              onClick={() => setActiveTab(item.id)}
              className={`w-full flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors ${
                activeTab === item.id
                  ? 'bg-primary text-primary-foreground'
                  : 'text-foreground hover:bg-secondary'
              }`}
            >
              <span className="text-lg">{item.icon}</span>
              {item.label}
            </button>
          ))}
        </nav>

        <div className="p-3 border-t">
          <div className="flex items-center gap-3 px-3 py-2 mb-2">
            <div className="w-9 h-9 rounded-full bg-accent flex items-center justify-center text-accent-foreground font-semibold text-sm">
              {firstName?.[0]?.toUpperCase()}
            </div>
            <div className="flex-1 min-w-0">
              <p className="text-sm font-medium truncate">{firstName}</p>
              <p className="text-xs text-muted-foreground truncate">{email}</p>
            </div>
          </div>
          <Button variant="outline" className="w-full" onClick={handleLogout}>
            Wyloguj się
          </Button>
        </div>
      </aside>

      <main className="flex-1 overflow-auto">
        <header className="px-8 py-6 border-b bg-background">
          <h1 className="text-2xl font-bold">Witaj, {firstName}! 👋</h1>
          <p className="text-muted-foreground text-sm mt-1">
            Oto podsumowanie Twojego konta
          </p>
        </header>

        <div className="p-8 space-y-6">
          <Card className="bg-gradient-to-br from-primary to-accent text-primary-foreground border-0 shadow-lg">
            <CardHeader>
              <CardDescription className="text-primary-foreground/80">
                Saldo dostępne
              </CardDescription>
              <CardTitle className="text-4xl font-bold mt-2">
                {loading && 'Ładowanie...'}
                {error && '—'}
                {account && formatBalance(account.balance, account.currency)}
              </CardTitle>
            </CardHeader>
            <CardContent>
              {error && (
                <div className="mb-3 p-2 rounded bg-destructive/20 text-sm">
                  {error}
                </div>
              )}
              <div className="flex justify-between text-sm text-primary-foreground/90">
                <div>
                  <p className="text-primary-foreground/70">Numer konta</p>
                  <p className="font-mono">
                    {loading && '...'}
                    {error && '—'}
                    {account && formatAccountNumber(account.accountNumber)}
                  </p>
                </div>
                <div className="text-right">
                  <p className="text-primary-foreground/70">Konto główne</p>
                  <p>{account?.currency ?? '—'}</p>
                </div>
              </div>
            </CardContent>
          </Card>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <QuickAction icon="💸" label="Przelew" />
            <QuickAction icon="📥" label="Doładuj" />
            <QuickAction icon="🏦" label="Załóż lokatę" />
            <QuickAction icon="📊" label="Weź kredyt" />
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Ostatnie transakcje</CardTitle>
              <CardDescription>Dane pokażą się tu wkrótce</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                <TransactionRow title="Biedronka" date="Dzisiaj, 14:32" amount="-23,40 zł" />
                <TransactionRow title="Wpłata - wypłata" date="Wczoraj, 09:15" amount="+5 000,00 zł" income />
                <TransactionRow title="Netflix" date="3 dni temu" amount="-43,00 zł" />
                <TransactionRow title="Allegro" date="5 dni temu" amount="-149,99 zł" />
              </div>
            </CardContent>
          </Card>
        </div>
      </main>
    </div>
  );
}

function QuickAction({ icon, label }: { icon: string; label: string }) {
  return (
    <button className="bg-card border rounded-xl p-4 flex flex-col items-center gap-2 hover:shadow-md hover:border-primary transition-all">
      <span className="text-2xl">{icon}</span>
      <span className="text-sm font-medium">{label}</span>
    </button>
  );
}

function TransactionRow({
  title,
  date,
  amount,
  income = false,
}: {
  title: string;
  date: string;
  amount: string;
  income?: boolean;
}) {
  return (
    <div className="flex items-center justify-between py-3 border-b last:border-0">
      <div>
        <p className="font-medium">{title}</p>
        <p className="text-xs text-muted-foreground">{date}</p>
      </div>
      <span className={`font-semibold ${income ? 'text-green-600' : 'text-foreground'}`}>
        {amount}
      </span>
    </div>
  );
}

export default DashboardPage;