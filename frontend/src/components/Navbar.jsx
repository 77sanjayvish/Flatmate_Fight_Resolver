import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { HiMenu, HiX } from 'react-icons/hi';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const navLinks = [
    { to: '/dashboard', label: 'Dashboard' },
    { to: '/complaints', label: 'Complaints' },
    { to: '/leaderboard', label: 'Leaderboard' },
    { to: '/profile', label: 'Profile' },
  ];

  const isActive = (path) => location.pathname === path;

  return (
    <nav className="bg-gradient-to-r from-indigo-600 to-purple-600 shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <Link to="/dashboard" className="flex items-center space-x-2">
            <span className="text-2xl">🏠</span>
            <span className="text-white font-bold text-lg hidden sm:block">
              Flatmate Fight Resolver
            </span>
            <span className="text-white font-bold text-lg sm:hidden">FFR</span>
          </Link>

          {user && (
            <>
              <div className="hidden md:flex items-center space-x-1">
                {navLinks.map((link) => (
                  <Link
                    key={link.to}
                    to={link.to}
                    className={`px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                      isActive(link.to)
                        ? 'bg-white/20 text-white'
                        : 'text-indigo-100 hover:bg-white/10 hover:text-white'
                    }`}
                  >
                    {link.label}
                  </Link>
                ))}
              </div>

              <div className="hidden md:flex items-center space-x-3">
                <span className="text-indigo-100 text-sm">
                  {user.userName || user.email}
                </span>
                <button
                  onClick={handleLogout}
                  className="bg-white/20 hover:bg-white/30 text-white px-3 py-1.5 rounded-md text-sm font-medium transition-colors"
                >
                  Logout
                </button>
              </div>

              <button
                className="md:hidden text-white"
                onClick={() => setMenuOpen(!menuOpen)}
              >
                {menuOpen ? <HiX size={24} /> : <HiMenu size={24} />}
              </button>
            </>
          )}
        </div>
      </div>

      {menuOpen && user && (
        <div className="md:hidden bg-indigo-700 pb-3 px-4 space-y-1">
          {navLinks.map((link) => (
            <Link
              key={link.to}
              to={link.to}
              onClick={() => setMenuOpen(false)}
              className={`block px-3 py-2 rounded-md text-sm font-medium ${
                isActive(link.to)
                  ? 'bg-white/20 text-white'
                  : 'text-indigo-100 hover:bg-white/10'
              }`}
            >
              {link.label}
            </Link>
          ))}
          <button
            onClick={handleLogout}
            className="block w-full text-left px-3 py-2 rounded-md text-sm font-medium text-indigo-100 hover:bg-white/10"
          >
            Logout
          </button>
        </div>
      )}
    </nav>
  );
}
